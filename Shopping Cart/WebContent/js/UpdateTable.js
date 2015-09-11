/**
 * Adding/Removing content from HTML page using jQuery
 */

// Add/Delete rows from the table
$(document)
		.ready(
				function() {
					$("#Add")
							.click(
									function() {
										$("#Wishlist")
												.append(
														'<tr><td>'
																+ $('#Name')
																		.val()
																+ '</td><td>'
																+ $(
																		'#Description')
																		.val()
																+ '</td><td>'
																+ $('#Price')
																		.val()
																+ '</td><td><a href="javascript:void(0);" class="remCF">Remove</a></td></tr>');
										document.getElementById("Wishmessage").innerHTML = "There are "
												+ (document
														.getElementById("Wishlist").rows.length - 1)
												+ " items you have wished for. Fill the below details to add more items.";
									});
					$("#Wishlist")
							.on(
									'click',
									'.remCF',
									function() {
										$(this).parent().parent().remove();
										document.getElementById("Wishmessage").innerHTML = "There are "
												+ (document
														.getElementById("Wishlist").rows.length - 1)
												+ " items you have wished for. Fill the below details to add more items.";

									});
				});

// Set background to white only if both mouse is over the element and alt key is
// pressed
$(document).ready(function() {
	var key = false;
	var element = null;

	$('html').keydown(function(e) {
		if (e.which === 18 && element != null)
			element.css("background-color", "white");
	});
	$('html').keyup(function(e) {
		if (e.which === 18)
			element.css("background-color", "#dcffb9");
	});
	$('td').mouseover(function() {
		if ($(this).closest('table').attr('id') === "CartSummary")
			element = $(this);
	});
	$('td').mouseleave(function() {
		if ($(this).closest('table').attr('id') === "CartSummary")
			element.css("background-color", "#dcffb9");

		element = null;
	});
});