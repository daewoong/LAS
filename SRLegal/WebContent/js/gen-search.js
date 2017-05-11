
/**  gen-search.js  */


jQuery(function($){
	
	genSearch.init();
	
});


var searchTerm = "";

var genSearch = function(){
	
	var sys;
	var dataSubject = "";
	var dataPredicate = "";
	var dataObject = "";
	var dataCount = 0;
	var keyword = "";
	var count = 0;
	var reSearch = 1;	
	var findIndex = 0;
	var tCount = 0;
	var mainReSearch = 0;
	var totalSubject = new Array();
	
	//meta 
	var metaLawName = [];
	var metaJoName = [];
	var metaLAWID = [];
	var metaLAWSID = [];
	var metaJJNO = [];
	var metaJONO = [];
	
	
	
	var colorType = {
			root: '#FF003D',
			subject: '#FF003D',
			predicate: '#FF003D',
			object: '#FF003D',
			literal: '#FF003D',
	};
	
	
	
	return {
		init: function(){
			
			//alert("genSearch init");
			
			$("#submitform").submit(function(){	
			//$("#submit").click(function(){	
				//$('#submitform').attr('title', 'your new title');
				
				//alert("Submitted");
				keyword = $("form").serialize();
				searchTerm = document.getElementById("textForm").value;
				
				
				//alert(keyword);
				if(mainReSearch){
					genSearch.tableDataInit(totalSubject);				
					genSearch.connectionAgent(keyword);;
				}else
					genSearch.connectionAgent(keyword);
				
				
				return false;
				
			});
						
            //Start the renderer
            this.sys = arbor.ParticleSystem({repulsion:2000, stiffness:200, friction:0.5, gravity: false, fps: 90, dt: 0.010, precision: 0});
			//this.sys = arbor.ParticleSystem({stiffness:200, repulsion:10, gravity: true, fps: 60, dt: 0.010});
			this.sys.renderer = Renderer("#canvasView");
		}, 

		connectionAgent:function(keyword){
			
			mainReSearch = 1;
			//Clear all previous particles
			this.sys.prune(function(node, from, to){return true;});
			
			$.ajax({  
	    		
	    		type: "post",
	    	    url : "http://localhost:9090/SNav/SNavAgent",
	    	    data : keyword,
	    	    contentType: "application/x-www-form-urlencoded; charset=MS949",
	    	    dataType : "jsonp",
	    	    jsonp : "callback",	    	    
	    	    success: genSearch.querySuccess,
				error : genSearch.queryError,
	    	});
		},
				
		querySuccess:function(data){
			
			if(data.count==0){
				alert("search failed");
			}else {
				//alert("query success");
				
	            if(data != null)    {            	
	                
	            	dataSubject = data.subject;
	                dataPredicate = data.predicate;
	                dataObject = data.object;
	                dataCount = data.count;

	                genSearch.dataGenerate();
	        
	            }else{
	            	alert("no data");
	            }
			}
			// $("form").unbind('submit').submit();
		},
		
		queryError:function(){
			 console.warn("There was an error: ");
			
		},
		
		dataGenerate:function(){
			
//			alert("triple generating");
			research = 1;
			
			//var rex = /[\[|\,|\s|\]]/;
			var rex = /\[|\,\s|\]/;
			var subject = dataSubject.split(rex);      
			var predicate = dataPredicate.split(rex);
			var object = dataObject.split(rex);
			
//			if(dataCount >= 20){
//				count = 20;
//			}else{
//				count = dataCount;
//			}
			count = dataCount;
			
			var next = 1;
			//var totalSubject = new Array();
			var recomCount = 0;
			var diffSIndex = 0;			
			var JoIndex = 0;
			
			
			for(var index=1; index<=count; index++){
				
				next++;
				
				if(index==1){
					//insert first subject
					totalSubject[recomCount] = subject[index];	
					
					recomCount++;
				}
				
				//make metadata structure
				//if(reSearch){	
				
					if(subject[index] == subject[next]){					
						
						if(predicate[index] == "법명"){
							metaLawName[diffSIndex] = object[index];
							continue;
						}else if(predicate[index] == "조문"){
							metaJoName[diffSIndex] = object[index];	
							continue;
						}else if(predicate[index] == "LAW_ID"){
							metaLAWID[diffSIndex] = object[index];
							continue;
						}else if(predicate[index] == "LAW_SID"){
							metaLAWSID[diffSIndex] = object[index];
							continue;
						}else if(predicate[index] == "JJ_NO"){
							metaJJNO[diffSIndex] = object[index];
							continue;
						}else if(predicate[index] == "JO_NO"){
							metaJONO[diffSIndex] = object[index];
							continue;
						}	
												
					}else if(index == count){	
						
						if(predicate[index] == "법명"){
							metaLawName[diffSIndex] = object[index];						
							continue;
						}else if(predicate[index] == "조문"){
							metaJoName[diffSIndex] = object[index];
							//alert("diff:" + diffSIndex + metaJoName[JoIndex]);
							continue;
						}else if(predicate[index] == "LAW_ID"){
							metaLAWID[diffSIndex] = object[index];
							continue;
						}else if(predicate[index] == "LAW_SID"){
							metaLAWSID[diffSIndex] = object[index];
							continue;
						}else if(predicate[index] == "JJ_NO"){
							metaJJNO[diffSIndex] = object[index];
							continue;
						}else if(predicate[index] == "JO_NO"){
							metaJONO[diffSIndex] = object[index];
							continue;
						}	
					}
					else{
						//subject save
						totalSubject[recomCount] = subject[next];
						
						if(predicate[index] == "법명"){
							metaLawName[diffSIndex] = object[index];
							//continue;
						}else if(predicate[index] == "조문"){
							metaJoName[diffSIndex] = object[index];								
							//continue;
						}else if(predicate[index] == "LAW_ID"){
							metaLAWID[diffSIndex] = object[index];
							//continue;
						}else if(predicate[index] == "LAW_SID"){
							metaLAWSID[diffSIndex] = object[index];
							//continue;
						}else if(predicate[index] == "JJ_NO"){
							metaJJNO[diffSIndex] = object[index];
							//continue;
						}else if(predicate[index] == "JO_NO"){
							metaJONO[diffSIndex] = object[index];
							//continue;
						}	
						
						//totalSubject[recomCount] = subject[next];	
						//alert(totalSubject[recomCount]);
						
						//subject count plus one.
						recomCount++;
						//different subject index plus one.
						diffSIndex++;
					}
					//next++;
				//}					
				
				//tripleTable
				genSearch.tripleTable(subject[index], predicate[index], object[index], index);
				
				//drawing Node
				genSearch.drawingNode(subject[index], object[index], predicate[index], index);
			}
			
			if(totalSubject.length == 1){
											
				var lawName = 0;
				var joName = 0;
				var lawID = 0;
				var lawSID = 0;
				var jjNO = 0;
				var joNO = 0;
				
				
				for(var index=1; index<=count; index++){
					//alert(predicate[index]);							
					if(index == count){	
						
						if(predicate[index] == "법명"){
							metaLawName[lawName++] = object[index];	
							continue;
						}else if(predicate[index] == "조문"){
							metaJoName[joName++] = object[index];
							continue;
						}else if(predicate[index] == "LAW_ID"){
							metaLAWID[lawID++] = object[index];
							continue;
						}else if(predicate[index] == "LAW_SID"){
							metaLAWSID[lawSID++] = object[index];
							continue;
						}else if(predicate[index] == "JJ_NO"){
							metaJJNO[jjNO++] = object[index];
							continue;
						}else if(predicate[index] == "JO_NO"){
							metaJONO[joNO++] = object[index];
							continue;
						}	
					}
					else {
						if(predicate[index] == "법명"){
							metaLawName[lawName++] = object[index];
							continue;
						}else if(predicate[index] == "조문"){
							metaJoName[joName++] = object[index];
							continue;
						}else if(predicate[index] == "LAW_ID"){
							metaLAWID[lawID++] = object[index];
							continue;
						}else if(predicate[index] == "LAW_SID"){
							metaLAWSID[lawSID++] = object[index];
							continue;
						}else if(predicate[index] == "JJ_NO"){
							metaJJNO[jjNO++] = object[index];
							continue;
						}else if(predicate[index] == "JO_NO"){
							metaJONO[joNO++] = object[index];
							continue;
						}	
					}															
				}
			}
			
			genSearch.metaTable(totalSubject, findIndex);
			genSearch.recomTable(totalSubject);
			
			//again search 
			//mainReSearch = 1;
			
		},
		
		//need to change parameter
		metaTable:function(totalSubject, findIndex){
			
			//meta search table
			var sTable = document.getElementById("searchResultTable"); 
			
			var metaIndex = metaLawName.length;		
			var metaJoIndex = metaJONO.length;		
			
			//alert(metaJoIndex);
			
			if(reSearch){
				
				for(var k=0; k<metaJoIndex; k++){
					
					//alert("meta : " + k + " -> " + metaJONO[k]);
					
					var sRow = sTable.insertRow(1);					
					var lNameCell = sRow.insertCell(0);
					var lNumCell = sRow.insertCell(1);
					//var search = sRow.insertCell(2);
					
					
					if(metaLAWID[k] == null){
						
						var lawNum = "http://121.140.240.218:19312/iplaw/ssologin.jsp?TYPE=sso&USER_ID=law&ACTION_TYPE=LAW_DITL&DOCMAP_GID=D1" +
			 			 "&LAW_ID=" + metaLAWID[0] +
			 			 "&LAW_SID=" + metaLAWSID[0] +
						 "&JJ_NO=" + metaJJNO[0] + 
						 "&JO_NO=" + metaJONO[k];	
						
						lNameCell.innerHTML = "";
						lNumCell.innerHTML = '<a target=_blank href = '+ lawNum + '>' + metaJoName[k]; + '</a>';	

					}else{
						
						var lawName = "http://121.140.240.218:19312/iplaw/ssologin.jsp?TYPE=sso&USER_ID=law&ACTION_TYPE=LAW_DITL&DOCMAP_GID=D1" +
						   "&LAW_ID=" + metaLAWID[k] +
					       "&LAW_SID=" + metaLAWSID[k];
						
						var lawNum = "http://121.140.240.218:19312/iplaw/ssologin.jsp?TYPE=sso&USER_ID=law&ACTION_TYPE=LAW_DITL&DOCMAP_GID=D1" +
			 			 "&LAW_ID=" + metaLAWID[k] +
			 			 "&LAW_SID=" + metaLAWSID[k] +
						 "&JJ_NO=" + metaJJNO[k] + 
						 "&JO_NO=" + metaJONO[k];				
					
						lNameCell.innerHTML = '<a target=_blank href = '+ lawName + '>' + metaLawName[k] + '</a>';
						lNumCell.innerHTML = '<a target=_blank href = '+ lawNum + '>' + metaJoName[k]; + '</a>';	
					}								
														
				}
			}else{

				var sRow = sTable.insertRow(1);		
				var lNameCell = sRow.insertCell(0);
				var lNumCell = sRow.insertCell(1);
				var search = sRow.insertCell(2);
				lNameCell.innerHTML = metaLawName[findIndex];
				lNumCell.innerHTML = metaJoName[findIndex];
			}
		},
		
		tripleTable:function(subject, predicate, object, index){
			
			tCount++;
			
			//triple table
			var table = document.getElementById("resultTable"); 
			var row = table.insertRow(1);
			
			var sCell = row.insertCell(0);
			var pCell = row.insertCell(1);
			var oCell = row.insertCell(2);
			//var kCell = row.insertCell(3);
			
			sCell.innerHTML = subject;
			pCell.innerHTML = predicate;
			oCell.innerHTML = object;		
			//kCell.innerHTML = "검색";
			
			//results count
			document.getElementById("count").innerHTML = "( " + tCount + "건 )";
			
		},	
		
		recomTable:function(recommendationSubject){
						
			//alert(recommendationSubject.length);
			//recom table
			for(var rCount=0; rCount<recommendationSubject.length; rCount++){
				var rTable = document.getElementById("recomTable");
				var row = rTable.insertRow(1);
				var rSCell = row.insertCell(0);
				//alert("rcout: " + rCount + " value : " + recommendationSubject[rCount]);
				rSCell.innerHTML = recommendationSubject[rCount];
			}
			var testc = 0;
			
			//table click event
			$('#recomTable td').live("click",function(){
				var originkeyword = $(this).html();
				keyword = "search=" + originkeyword;
				reSearch = 1;	
				
				for(var metaLawNameIndex=0; metaLawNameIndex<recommendationSubject.length; metaLawNameIndex++){

					if(originkeyword == recommendationSubject[metaLawNameIndex]){
						
						//need to change parameter
						findIndex = metaLawNameIndex;																	
						genSearch.tableDataInit(recommendationSubject);
						
						//Triple Result table count init.
						tCount = 0;
						genSearch.connectionAgent(keyword);
						break;
					}
				}
				
				testc = 0;
				
//				genSearch.tableDataInit(recommendationSubject);
//				genSearch.connectionAgent(keyword);
				
			});
			
		},	
		
		tableDataInit:function(recommendationSubject){
	
			
			for(var index=0; index<metaLawName.length; index++){	
				document.getElementById("searchResultTable").deleteRow(1);				
			}			
			
			for(var index=0; index<recommendationSubject.length; index++){
				document.getElementById("recomTable").deleteRow(1);
			}
			
			for(var index=0; index<tCount; index++){				
				document.getElementById("resultTable").deleteRow(1);				
			}
			
			//window.top.location.reload();
			
		},
		
		drawingNode:function(subject, object, predicate, index){
						
			//add node
			
		    node1 = this.sys.addNode(subject, {mass:1, color:"skyblue"});
		    node2 = this.sys.addNode(object, {mass:1, color:"white"});
		    //node3 = this.sys.addNode(object, {mass:2, color:"white"});
		    //node3 = sys.addEdge(predicate,{'index':index});
		    
		    //add edge

		    edge1 = this.sys.addEdge(node1, node2,{name: predicate, length:9.75, pointSize:5});

		},
		
		removeNodes: function(name, level){
			
			if(level === 2){
				//Clicked node
				var clicked = sys.getNode(name);
				
				//Clicked parent node
				var parent = sys.getNode(clicked.data.parent);
				
				//Parent is nolonger expanded
				parent.data.expanded = false;
				
				//Remove Children
				sys.prune(function(node, from, to){
					if(node.data.parent === name){
						return true;
					}
				});
				
				//Remove clicked node too
				sys.pruneNode(name);
			}
		}
		
	};
}();

var Renderer = function(canvas){

	var canvas = $(canvas).get(0);
    var ctx = canvas.getContext("2d");
   
    var particleSystem;

    //alert("RDF Triple Rendering");
    
    return {
      		
      init:function(system){
    	     	 		    
        // save a reference to the particle system for use in the .redraw() loop
        particleSystem = system;

        // the new dimensions
        particleSystem.screenSize(canvas.width, canvas.height) ;
        
        // leave an extra 80px of whitespace per side
        particleSystem.screenPadding(80); 
        
        // set up some event handlers to allow for node-dragging
        this.initMouseHandling();  
                  
      },
      
      redraw:function()
      {
          ctx.fillStyle = "white";
          ctx.fillRect (0,0, canvas.width, canvas.height);
          
          particleSystem.eachEdge (function (edge, pt1, pt2)
          {
        	 
              ctx.strokeStyle = "rgba(0,0,0, .333)";
              ctx.lineWidth = 1;
              ctx.beginPath ();
              ctx.moveTo (pt1.x, pt1.y);
              ctx.lineTo (pt2.x, pt2.y);
              ctx.stroke ();
              
//             var len = edge.data.name.length;
              var edgeName = edge.data.name;
              
//              if(len>=10){
//            	edgeName = edgeName.substring(0,10) + "...";  
//            	
//              }
              
             // alert(len);
             
//              alert(edge.data.name);
              if(edge.data.name == searchTerm){           	  
            	  //italic
                  ctx.fillStyle = "#680000";             	 
                  ctx.font = 'bold 15px Courier';
                  //ctx.fillText (edge.data.name, (pt1.x + pt2.x) / 2, (pt1.y + pt2.y) / 2);
                  ctx.fillText (edgeName, (pt1.x + pt2.x) / 2, (pt1.y + pt2.y) / 2);
                  
              }else{
            	  //italic
                  ctx.fillStyle = "#5C85AD";            
                  ctx.font = 'bold 12px sans-serif';
                  //ctx.fillText (edge.data.name, (pt1.x + pt2.x) / 2, (pt1.y + pt2.y) / 2);
                  ctx.fillText (edgeName, (pt1.x + pt2.x) / 2, (pt1.y + pt2.y) / 2);
              }                        
          });

          particleSystem.eachNode (function (node, pt)
          {      
		        	         	  
        	  var w = 15;      	       	             
              ctx.beginPath();
              
              ctx.arc(pt.x-w/2, pt.y-w/2, 20, 0, 2 * Math.PI, false);
              //ctx.fillStyle = 'skyblue';
              //ctx.fillStyle = node.data.color;  //node color            
              ctx.fillStyle = node.data.color;  //node color
              
              ctx.fill();
              ctx.lineWidth = 5;
              
              var len = node.name.length;
              var nodeName = node.name;
              
              if(len>10){
            	nodeName = nodeName.substring(0,8) + "..";  
          	
              }
              
             //ctx.fillStyle = "#D6E0EB";        
              if(node.name == searchTerm){
	              ctx.fillStyle = "#680000";
	              ctx.font = 'bold 15px sans-serif';
	              ctx.fillText (nodeName, pt.x-15, pt.y);
              }else{
            	  ctx.fillStyle = "gray";
	              ctx.font = 'bold 13px sans-serif';
	              ctx.fillText (nodeName, pt.x-15, pt.y);
              }
              //ctx.strokeStyle = '#003300';
              
              
              
              ctx.stroke();
          });       
      },
                       
      initMouseHandling:function(){

        // no-nonsense drag and drop (thanks springy.js)
        var dragged = null;

        // set up a handler object that will initially listen for mousedowns then
        // for moves and mouseups while dragging
        var handler = {
         	
          /** clicked */
          clicked:function(e){
            var pos = $(canvas).offset();
            _mouseP = arbor.Point(e.pageX-pos.left, e.pageY-pos.top);
            dragged = particleSystem.nearest(_mouseP);
		           		              
            if (dragged && dragged.node !== null){
              // while we're dragging, don't let physics move the node
              dragged.node.fixed = true;
     
            }
            
            $(canvas).bind('mousemove', handler.dragged);    
//            $(canvas).bind('mouseover', handler.over);  
            $(window).bind('mouseup', handler.dropped);

            return false;
          },
          
          /** dragged */
          dragged:function(e){
        	  
            var pos = $(canvas).offset();
            var s = arbor.Point(e.pageX-pos.left, e.pageY-pos.top);

            if (dragged && dragged.node !== null){
              var p = particleSystem.fromScreen(s);
              dragged.node.p = p;
            }

            return false;
          },
          
          /** dropped */
          dropped:function(e){
        	  
            if (dragged===null || dragged.node===undefined) return;
            if (dragged.node !== null) dragged.node.fixed = false;
            dragged.node.tempMass = 1000;
            
            //03.18
	        var id = dragged.node.name;		              
//	        alert(id);
	        
	        $(canvas).attr('title', id);  
	        //$.print('sdsdsdd');
	        
            dragged = null;
            
            $(canvas).unbind('mousemove', handler.dragged);           
            $(window).unbind('mouseup', handler.dropped);
            _mouseP = null;

            return false;
          },
          
          over:function(e){
        	  
        	 alert("over");
        	 
//             $(canvas).unbind('mousemove', handler.dragged);    
//             $(canvas).unbind('mouseover', handler.over);  
//             $(window).unbind('mouseup', handler.dropped);

          }
        };
        
	    // start listening
	    $(canvas).mousedown(handler.clicked);
	    $(canvas).mousemove(handler.moved);
	    //$(canvas).mouseover(handler.over);
	        
      }//init mousthandler	  
     
   };//return
   
 };//Renderer
		