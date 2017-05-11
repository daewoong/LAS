package kr.ac.ssu.dss.SRLegal.document.converter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;

import kr.ac.ssu.dss.SRLegal.document.converter.hwp.HwpTextExtractorV5;

import org.junit.Test;

public class TestHwpV5Extractor {
	private String extract(String path) throws FileNotFoundException,
			IOException {
		File file = new File(path);
		// System.out.println(file.getAbsolutePath());
		StringWriter writer = new StringWriter(4096);
		HwpTextExtractorV5.extractText(file, writer);
		return writer.toString();
	}

	/**
	 * 디버그.. 문자와 코드값 출력
	 * 
	 * @param t
	 * @return
	 */
	private String withCode(String t) {
		StringWriter writer = new StringWriter(4096);
		for (int ii = 0; ii < t.length(); ii++) {
			char ch = t.charAt(ii);
			if (ch == ' ' || ch == '\n')
				continue;
			writer.append(ch);
			if (ch >= 128) {
				writer.append("\t").append(String.format("0x%1$04x", (int) ch));
			}
			writer.append("\n");
		}
		return writer.toString();
	}

	private String extractIgnoreException(String path) {
		try {
			return extract(path);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Test
	public void testExtractText() throws IOException, ClassNotFoundException {
		System.out.println(extract("doc/documents/2011.hwp"));

	}
}