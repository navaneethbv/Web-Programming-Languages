/**
 * File to get book information from XPath queries
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

/**
 * Return the nodes filtered by a XPath query
 * 
 * @param xml
 *            XML response object
 * @param path
 *            XPath query
 * @returns nodes found
 */
function getResults(xml, path) {
	// Code for IE
	if (window.ActiveXObject || xhttp.responseType == "msxml-document") {
		xml.setProperty("SelectionLanguage", "XPath");
		nodes = xml.selectNodes(path);

		return nodes;
	}

	// Code for other browsers
	else if (document.implementation && document.implementation.createDocument) {
		var nodes = xml.evaluate(path, xml, null, XPathResult.ANY_TYPE, null);

		return nodes;
	}
}

/**
 * Gets all authors in the element as a array
 * 
 * @param element
 *            Element to fetch authors from
 * @returns {String} array
 */
function getAuthors(element) {
	// Get authors
	var author = element.getElementsByTagName("author");
	var details = [];
	details[0] = author[0].getElementsByTagName("firstname")[0].childNodes[0].nodeValue
			+ " ";
	details[0] += author[0].getElementsByTagName("middlename").length !== 0 ? author[0]
			.getElementsByTagName("middlename")[0].childNodes[0].nodeValue
			+ " "
			: "";
	details[0] += author[0].getElementsByTagName("lastname")[0].childNodes[0].nodeValue;

	for (i = 1; i < author.length; i++) {
		details[i] = author[i].getElementsByTagName("firstname")[0].childNodes[0].nodeValue
				+ " ";
		details[i] += author[i].getElementsByTagName("middlename").length !== 0 ? author[i]
				.getElementsByTagName("middlename")[0].childNodes[0].nodeValue
				+ " "
				: "";
		details[i] += author[i].getElementsByTagName("lastname")[0].childNodes[0].nodeValue;
	}

	// Return authors
	return details;
}

// Load XML
var xmlhttp = loadXMLDoc("Books.xml");
xmlDoc = xmlhttp.responseXML;

// Question i
path = "/Books/book/price";
var nodes = getResults(xmlDoc, path);
var result = nodes.iterateNext();
var totalPrice = 0.0;

// Calculate total price
while (result) {
	totalPrice += parseFloat(result.childNodes[0].nodeValue);
	result = nodes.iterateNext();
}

document.write("<h1>Total cost of all the books</h1>");
document.write(totalPrice);
document.write("<br>");

// Question ii
// Get publishers
path = "//publisher/name";
var publishers = getResults(xmlDoc, path);
var nodes = publishers.iterateNext();
var publisher = {};

// Get unique publishers in an map
while (nodes) {
	if (publisher[nodes.childNodes[0].nodeValue] == null)
		publisher[nodes.childNodes[0].nodeValue] = 1;

	nodes = publishers.iterateNext();
}

// Create author to publisher mapping with price
var publishers = getResults(xmlDoc, path);
var nodes = publishers.iterateNext();
var result = {};
while (nodes) {
	var key = nodes.childNodes[0].nodeValue;

	var authors = getAuthors(nodes.parentNode.parentNode);
	for (i = 0; i < authors.length; i++) {
		// Set if author not found
		if (result[authors[i]] == null)
			result[authors[i]] = {};

		// Set price if publisher not found, else add the price for that
		// publisher
		if (result[authors[i]][key] == null)
			result[authors[i]][key] = parseFloat(nodes.parentNode.parentNode
					.getElementsByTagName("price")[0].childNodes[0].nodeValue);
		else
			result[authors[i]][key] += parseFloat(nodes.parentNode.parentNode
					.getElementsByTagName("price")[0].childNodes[0].nodeValue);
	}

	nodes = publishers.iterateNext();
}

// Write the result
// Write the table header
document
		.write("<h1>Total cost of all books written by an author for a particular publisher</h1>"
				+ "<div class=\"CSSTableGenerator\"><table><tr><th></th>");
for (a in result)
	document.write("<th>" + a + "</th>");

document.write("</tr>");
for (p in publisher) {
	// First cell is the publisher name
	document.write("<tr><td>" + p + "</td>");

	// Write the total price
	for (a in result) {
		if (result[a][p] == null)
			document.write("<td>$0</td>");
		else
			document.write("<td>$" + result[a][p] + "</td>");
	}

	document.write("</tr>");
}

document.write("</table></div>")

// Question iii
path = "/Books/book/author[education='MS' or education='MBA']/..";
nodes = getResults(xmlDoc, path);
result = nodes.iterateNext();

// Write elements
document
		.write("<h1>Name and book titles of all authors that have an MS or MBA</h1>"
				+ "<div class=\"CSSTableGenerator\"><table><tr><th>Name</th><th>Title</th></tr>");
while (result) {
	var details = getAuthors(result);
	for (i = 0; i < details.length; i++)
		document
				.write("<tr><td>"
						+ details[i]
						+ "</td><td>"
						+ result.getElementsByTagName("title")[0].childNodes[0].nodeValue
						+ "</td></tr>");

	result = nodes.iterateNext();
}

document.write("</table></div>");

// Question iv
path = "/Books/book[price<100.00]";
nodes = getResults(xmlDoc, path);
result = nodes.iterateNext();

// Write elements
document
		.write("<h1>Books that cost less than $100</h1>"
				+ "<div class=\"CSSTableGenerator\"><table><tr><th>Title</th><th>Author</th>"
				+ "<th>Publisher</th></tr>");
while (result) {
	// Get authors
	var details = getAuthors(result);
	var authors = details[0];
	for (i = 1; i < details.length; i++)
		authors += "\n" + details[i];

	document.write("<tr><td>"
			+ result.getElementsByTagName("title")[0].childNodes[0].nodeValue
			+ "</td><td>"
			+ authors
			+ "</td><td>"
			+ result.getElementsByTagName("publisher")[0]
					.getElementsByTagName("name")[0].childNodes[0].nodeValue
			+ "</td></tr>");
	result = nodes.iterateNext();
}

document.write("</table></div>");

// Question v
path = "/Books/book[number(translate(publicationdate, '-', '')) > 20101231]";
nodes = getResults(xmlDoc, path);
result = nodes.iterateNext();

// Write elements
document
		.write("<h1>Books that were published after 2010</h1>"
				+ "<div class=\"CSSTableGenerator\"><table><tr><th>Title</th><th>Author</th>"
				+ "<th>Publisher</th></tr>");
while (result) {
	// Get authors
	var details = getAuthors(result);
	var authors = details[0];
	for (i = 1; i < details.length; i++)
		authors += "\n" + details[i];

	document.write("<tr><td>"
			+ result.getElementsByTagName("title")[0].childNodes[0].nodeValue
			+ "</td><td>"
			+ authors
			+ "</td><td>"
			+ result.getElementsByTagName("publisher")[0]
					.getElementsByTagName("name")[0].childNodes[0].nodeValue
			+ "</td></tr>");
	result = nodes.iterateNext();
}

document.write("</table></div>");