/*
 * Copyright (C) 2013 argonet.co.kr <ddoleye@gmail.com>
 * 
 * This library is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * 본 제품은 한글과컴퓨터의 한글 문서 파일(.hwp) 공개 문서를 참고하여 개발하였습니다.
 * 
 * 본 제품은 다음의 소스를 참조하였습니다.
 * https://github.com/cogniti/ruby-hwp/
 * https://github.com/cogniti/libghwp/
 */
package kr.ac.ssu.dss.SRLegal.document.converter.hwp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.DocumentInputStream;
import org.apache.poi.poifs.filesystem.Entry;
import org.apache.poi.poifs.filesystem.NDocumentInputStream;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.util.LittleEndian;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class HwpTextExtractorV5 {
	protected static Logger log = LoggerFactory
			.getLogger(HwpTextExtractorV5.class);

	private static final byte[] HWP_V5_SIGNATURE = "HWP Document File"
			.getBytes();

	private static final int[] HWP_CONTROL_CHARS = new int[] { 0, 10, 13, 24,
			25, 26, 27, 28, 29, 30, 31 };
	private static final int[] HWP_INLINE_CHARS = new int[] { 4, 5, 6, 7, 8, 9,
			19, 20 };
	private static final int[] HWP_EXTENDED_CHARS = new int[] { 1, 2, 3, 11,
			12, 14, 15, 16, 17, 18, 21, 22, 23 };

	private static final int HWPTAG_BEGIN = 0x010;

	/**
	 * HWP 파일에서 텍스트 추출
	 * 
	 * @param source
	 * @param writer
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static boolean extractText(File source, Writer writer)
			throws FileNotFoundException, IOException {
		if (source == null)
			throw new IllegalArgumentException();
		if (!source.exists())
			throw new FileNotFoundException();

		NPOIFSFileSystem fs = null;
		try {
			FileHeader header;

			// HWP Document가 맞는지 확인한다
			try {
				// 우선은 Compound File
				fs = new NPOIFSFileSystem(source);
				header = getHeader(fs);
			} catch (IOException e) {
				log.warn("파일정보 확인 중 오류. HWP 포맷이 아닌 것으로 간주함", e);
				return false;
			}

			if (header == null)
				return false;

			// TODO 배포용 문서.. BodyText 가 아닌 ViewText에 Section 이 존재하고
			// 아직까지 이걸 읽는 방법이 없다능?
			// https://groups.google.com/forum/#!topic/libhwp/raZpuBS2BX4
			if (header.viewtext) {
				log.warn("배포용 문서는 해석할 수 없습니다. https://groups.google.com/forum/#!topic/libhwp/raZpuBS2BX4");
				// return or throw exception
				return false;
			}

			// 여기까지 왔다면 HWP 문서가 맞다고 본다
			// 이제부터의 IOException 은 HWP 읽는 중 오류이다.
			extractText(header, fs, writer);

			return true;
		} finally {
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e) {
					log.warn("Exception", e);
				}
			}
		}
	}

	/**
	 * HWP의 FileHeader 추출
	 * 
	 * @param fs
	 * @return
	 * @throws IOException
	 */
	private static FileHeader getHeader(NPOIFSFileSystem fs) throws IOException {
		DirectoryNode root = fs.getRoot();

		// 파일인식정보 p.18

		// FileHeader 존재 여부
		Entry headerEntry = root.getEntry("FileHeader");
		if (!headerEntry.isDocumentEntry())
			return null;

		// 시그니처 확인
		byte[] header = new byte[256]; // FileHeader 길이는 256
		DocumentInputStream headerStream = new DocumentInputStream(
				(DocumentEntry) headerEntry);
		try {
			int read = headerStream.read(header);
			if (read != 256
					|| !Arrays.equals(HWP_V5_SIGNATURE, Arrays.copyOfRange(
							header, 0, HWP_V5_SIGNATURE.length)))
				return null;
		} finally {
			headerStream.close();
		}

		FileHeader fileHeader = new FileHeader();

		// 버전. debug
		fileHeader.version = HwpVersion.parseVersion(LittleEndian.getUInt(
				header, 32));
		long flags = LittleEndian.getUInt(header, 36);
		log.debug("Flags={}", Long.toBinaryString(flags).replace(' ', '0'));

		fileHeader.compressed = (flags & 0x01) == 0x01;
		fileHeader.encrypted = (flags & 0x02) == 0x02;
		fileHeader.viewtext = (flags & 0x04) == 0x04;

		return fileHeader;
	}

	/**
	 * 텍스트 추출
	 * 
	 * @param writer
	 * @param source
	 * 
	 * @return
	 * @throws IOException
	 */
	private static void extractText(FileHeader header, NPOIFSFileSystem fs,
			Writer writer) throws IOException {
		DirectoryNode root = fs.getRoot();

		// BodyText 읽기
		Entry bodyText = root.getEntry("BodyText");
		if (bodyText == null || !bodyText.isDirectoryEntry())
			throw new IOException("Invalid BodyText");

		Iterator<Entry> iterator = ((DirectoryEntry) bodyText).getEntries();
		while (iterator.hasNext()) {
			Entry entry = iterator.next();
			if (entry.getName().startsWith("Section")
					&& entry instanceof DocumentEntry) {
				log.debug("extract {}", entry.getName());

				InputStream input = new NDocumentInputStream(
						(DocumentEntry) entry);
				if (header.compressed)
					input = new InflaterInputStream(input, new Inflater(true));

				HwpStreamReader sectionStream = new HwpStreamReader(input);

				try {
					extractText(sectionStream, writer);
				} finally {
					// 닫을 필요는 없을 것이다
					try {
						input.close();
					} catch (IOException e) {
						log.error("있을 수 없는 일?", e);
					}
				}
			} else {
				log.warn("알수없는 Entry '{}'({})", entry.getName(), entry);
			}
		}
	}

	/**
	 * Section 스트림에서 문자를 추출
	 * 
	 * @param sectionStream
	 * @param writer
	 * @throws IOException
	 */
	private static void extractText(HwpStreamReader sectionStream, Writer writer)
			throws IOException {
		StringBuffer buf = new StringBuffer(1024);
		TagInfo tag = new TagInfo();

		while (true) {
			if (!readTag(sectionStream, tag))
				break;

			buf.setLength(0);
			if (HWPTAG_BEGIN + 50 == tag.id) {
				writeParaHeader(sectionStream, tag.length, buf);
			} else if (HWPTAG_BEGIN + 51 == tag.id) {
				if (tag.length % 2 != 0)
					throw new IOException("Invalid block size");

				writeParaText(sectionStream, tag.length, buf);

				if (buf.length() > 0) // 줄바꿈 추가?
					writer.append(buf.toString()).append('\n');
			} else {
				sectionStream.ensureSkip(tag.length);
			}

			if (buf.length() > 0) {
				log.debug("TAG[{}]({}):{} [{}]", new Object[] { tag.id,
						tag.level, tag.length, buf });
			}
		}
	}

	private static void writeParaHeader(HwpStreamReader sectionStream,
			long length, StringBuffer buf) throws IOException {
		// log.debug("text={}", sectionStream.uint32());
		// log.debug("control mask={}", sectionStream.uint32());
		// log.debug("문단모양아이디참조값={}", sectionStream.uint16());
		// log.debug("문단스타일아이디참조값={}", sectionStream.uint8());
		// log.debug("단나누기종류={}", sectionStream.uint8());
		// log.debug("글자모양정보수={}", sectionStream.uint16());
		// log.debug("range tag정보수={}", sectionStream.uint16());
		// log.debug("각줄에 대한 align정보수={}", sectionStream.uint16());
		// log.debug("문단 Instance ID={}", sectionStream.uint32());
		// sectionStream.ensureSkip(2);

		sectionStream.ensureSkip(length);
	}

	/**
	 * HWPTAG_PARA_TEXT 의 문자스트림을 문자열로 변환
	 * 
	 * @param sectionStream
	 * @param datasize
	 * @param buf
	 * @throws IOException
	 */
	private static void writeParaText(HwpStreamReader sectionStream,
			long datasize, StringBuffer buf) throws IOException {
		int[] chars = sectionStream.uint16((int) (datasize / 2));

		for (int index = 0; index < chars.length; index++) {
			int ch = chars[index];
			if (Arrays.binarySearch(HWP_INLINE_CHARS, ch) >= 0) {
				if (ch == 9) {
					buf.append('\t');
				}
				index += 7;
			} else if (Arrays.binarySearch(HWP_EXTENDED_CHARS, ch) >= 0) {
				index += 7;
			} else if (Arrays.binarySearch(HWP_CONTROL_CHARS, ch) >= 0) {
				buf.append(' ');
			} else {
				buf.append((char) ch);
			}
		}
	}

	private static boolean readTag(HwpStreamReader sectionStream, TagInfo tag)
			throws IOException {
		// p.24

		long recordHeader = sectionStream.uint32();
		if (recordHeader == -1)
			return false;

		// log.debug("Record Header={} [{}]", recordHeader,
		// Long.toHexString(recordHeader));

		tag.id = recordHeader & 0x3FF;
		tag.level = (recordHeader >> 10) & 0x3FF;
		tag.length = (recordHeader >> 20) & 0xFFF;

		// 확장 데이터 레코드 p.24
		if (tag.length == 0xFFF)
			tag.length = sectionStream.uint32();

		return true;
	}

	static class FileHeader {
		HwpVersion version;
		boolean compressed; // bit 0
		boolean encrypted; // bit 1
		boolean viewtext; // bit 2
	}

	static class TagInfo {
		long id;
		long level;
		long length;
	}

	static class HwpVersion {
		int m;
		int n;
		int p;
		int r;

		public String toString() {
			return String.format("%d.%d.%d.%d", m, n, p, r);
		}

		public static HwpVersion parseVersion(long longVersion) {
			HwpVersion version = new HwpVersion();
			version.m = (int) ((longVersion & 0xFF000000L) >> 24);
			version.n = (int) ((longVersion & 0x00FF0000L) >> 16);
			version.p = (int) ((longVersion & 0x0000FF00L) >> 8);
			version.r = (int) ((longVersion & 0x000000FFL));
			return version;
		}
	}

}
