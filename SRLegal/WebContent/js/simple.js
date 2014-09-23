

$(document).ready(function(){
	
  alert("Legal Semantic Retrieval"); 
  
  var graph = new Springy.Graph();
  graph.addNodes('Dennis', 'Michael', 'Jessica', 'Timothy', 'Barbara')
  graph.addNodes('Amphitryon', 'Alcmene', 'Iphicles', 'Heracles', '법령');
  graph.addNodes('판례', '조문', '번호');
  
  graph.addEdges(
	  ['Dennis', 'Michael', {color: '#00A0B0', label: 'Foo bar'}],
	  ['Michael', 'Dennis', {color: '#6A4A3C'}],
	  ['Michael', 'Jessica', {color: '#CC333F'}],
	  ['Jessica', 'Barbara', {color: '#EB6841'}],
	  ['Michael', 'Timothy', {color: '#EDC951'}],
	  ['Amphitryon', 'Alcmene', {color: '#7DBE3C'}],
	  ['Alcmene', 'Amphitryon', {color: '#BE7D3C'}],
	  ['Amphitryon', 'Iphicles'],
	  ['Amphitryon', 'Heracles', {color: '#BE7D3C'}],
	  ['Amphitryon', '법령',{color: '#CC333F'}],
	  ['Amphitryon', '판례',{color: '#CC333F'}],
	  ['Amphitryon', '조문',{color: '#BE7D3C'}],
	  ['Amphitryon', '번호'],
	  ['Barbara', 'Timothy', {color: '#6A4A3C'}]
  );
  
 // $("#results_1").text("results_1");
 // $("#results_2").text("results_2");
  
  $("#submitform").submit(function(){
	  alert("submit"); 
	  $("p").append(" <b>Appended text</b>.");
	  $("p").append(" <b>Appended text2</b>.");
  });
  
  
  var springy = jQuery('#viewport').springy({
	    graph: graph
  });	
	

  
});
