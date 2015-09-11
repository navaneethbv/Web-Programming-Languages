/**
 * File to parse XML into a HTML table
 */

/**
 * Loads XML document
 * 
 * @param dname
 *            file name
 * @returns {___anonymous_xhttp}
 */
function loadXMLDoc(dname) {
	if (window.XMLHttpRequest) {
		xhttp = new XMLHttpRequest();
	} else {
		xhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xhttp.open("GET", dname, false);
	try {
		xhttp.responseType = "msxml-document";
	} catch (err) {
	}

	xhttp.send();
	return xhttp;
}

var xmlhttp = loadXMLDoc("Books.xml");
xmlDoc = xmlhttp.responseXML;

// Create table with headers
document
		.write("<div class=\"CSSTableGenerator\"><table><tr><th>Title</th><th>ISBN</th>"
				+ "<th>Price</th><th>Publication Date</th><th>Total Circulation Time</th>"
				+ "<th>Author</th><th>Publisher</th></tr>");

var x = xmlDoc.getElementsByTagName("book");
for (i = 0; i < x.length; i++) {
	// Write simple elements
	document.write("<tr><td>");
	document
			.write(x[i].getElementsByTagName("title")[0].childNodes[0].nodeValue);
	document.write("</td><td>");
	document
			.write(x[i].getElementsByTagName("isbn")[0].childNodes[0].nodeValue);
	document.write("</td><td>");
	document
			.write(x[i].getElementsByTagName("price")[0].childNodes[0].nodeValue);
	document.write("</td><td>");
	document
			.write(x[i].getElementsByTagName("publicationdate")[0].childNodes[0].nodeValue);
	document.write("</td><td>");
	document
			.write(x[i].getElementsByTagName("time")[0].childNodes[0].nodeValue);

	// Get the author details in a string
	var author = x[i].getElementsByTagName("author");
	var details = author[0].getElementsByTagName("firstname")[0].childNodes[0].nodeValue
			+ " ";
	details += author[0].getElementsByTagName("middlename").length !== 0 ? author[0]
			.getElementsByTagName("middlename")[0].childNodes[0].nodeValue
			+ " "
			: "";
	details += author[0].getElementsByTagName("lastname")[0].childNodes[0].nodeValue
			+ ", ";
	details += author[0].getElementsByTagName("education")[0].childNodes[0].nodeValue
			+ " (";
	details += author[0].getElementsByTagName("bday")[0].childNodes[0].nodeValue
			+ ")\n\t";

	// Get the address for author
	var address = author[0].getElementsByTagName("address")[0];
	details += address.getElementsByTagName("street_name")[0].childNodes[0].nodeValue
			+ ", ";
	details += address.getElementsByTagName("Apt").length !== 0 ? "Apt "
			+ address.getElementsByTagName("Apt")[0].childNodes[0].nodeValue
			+ ", " : "";
	details += address.getElementsByTagName("city")[0].childNodes[0].nodeValue
			+ "-";
	details += address.getElementsByTagName("zipcode")[0].childNodes[0].nodeValue
			+ " ";
	details += address.getElementsByTagName("country")[0].childNodes[0].nodeValue;
	
	for (j = 1; j < author.length; j++) {
		details += "\n"
				+ author[j].getElementsByTagName("firstname")[0].childNodes[0].nodeValue
				+ " ";
		details += author[j].getElementsByTagName("middlename").length !== 0 ? author[j]
				.getElementsByTagName("middlename")[0].childNodes[0].nodeValue
				+ " "
				: "";
		details += author[j].getElementsByTagName("lastname")[0].childNodes[0].nodeValue
				+ ", ";
		details += author[j].getElementsByTagName("education")[0].childNodes[0].nodeValue
				+ " (";
		details += author[j].getElementsByTagName("bday")[0].childNodes[0].nodeValue
				+ ")\n\t";
		
		address = author[j].getElementsByTagName("address")[0];
		details += address.getElementsByTagName("street_name")[0].childNodes[0].nodeValue
				+ ", ";
		details += address.getElementsByTagName("Apt").length !== 0 ? "Apt "
				+ address.getElementsByTagName("Apt")[0].childNodes[0].nodeValue
				+ ", " : "";
		details += address.getElementsByTagName("city")[0].childNodes[0].nodeValue
				+ "-";
		details += address.getElementsByTagName("zipcode")[0].childNodes[0].nodeValue
				+ " ";
		details += address.getElementsByTagName("country")[0].childNodes[0].nodeValue;
	}
	
	// Write author
	document.write("</td><td>");
	document.write(details);

	// Get the publisher details in a string
	var publisher = x[i].getElementsByTagName("publisher")[0];
	details = publisher.getElementsByTagName("name")[0].childNodes[0].nodeValue
			+ "\n";

	// Get the address for publisher
	address = publisher.getElementsByTagName("address")[0];
	details += address.getElementsByTagName("street_name")[0].childNodes[0].nodeValue
			+ ", ";
	details += address.getElementsByTagName("Suite").length !== 0 ? "Suite "
			+ address.getElementsByTagName("Suite")[0].childNodes[0].nodeValue
			+ ", " : "";
	details += address.getElementsByTagName("city")[0].childNodes[0].nodeValue
			+ "-";
	details += address.getElementsByTagName("zipcode")[0].childNodes[0].nodeValue
			+ " ";
	details += address.getElementsByTagName("country")[0].childNodes[0].nodeValue;

	// Write publisher
	document.write("</td><td>");
	document.write(details);
	document.write("</td></tr>");
}
document.write("</table></div>");