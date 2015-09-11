/**
 * Functions and scripts needed for shopping cart
 */

// Initialize shopping cart
simpleCart({
	checkout : {
		type : "PayPal",
		email : "exg140230@utdallas.edu"
	},
	cartStyle : "table"
});

// 1. Functions for Click event - Hide, Show and Toggle
// Examples for ID and class HTML selectors
$(document).ready(function() {
	$("#hide").click(function() {
		$(".simpleCart_items").hide();
	});
	$("#show").click(function() {
		$(".simpleCart_items").show();
	});
	$("#toggle").click(function() {
		$("#CartSummary").toggle();
	});
});

// 2. Functions for DblClick event - FadeIn, FadeOut, FadeTo and FadeToggle
// Examples for attribute value HTML selectors
$("#Basketball").dblclick(function() {
	$("span:contains('gridiron')").fadeOut("slow");
	$("span:contains('diamond-shaped')").fadeOut("slow");
	$("span:contains('netted hoop')").fadeIn("slow");
	$("#Basketball").fadeTo("slow", 1.0);
	$("#Football").fadeTo("slow", 0.5);
	$("#Baseball").fadeTo("slow", 0.5);
});
$("#Football").dblclick(function() {
	$("span:contains('netted hoop')").fadeOut("slow");
	$("span:contains('diamond-shaped')").fadeOut("slow");
	$("span:contains('gridiron')").fadeIn("slow");
	$("#Basketball").fadeTo("slow", 0.5);
	$("#Football").fadeTo("slow", 1.0);
	$("#Baseball").fadeTo("slow", 0.5);
});
$("#Baseball").dblclick(function() {
	$("span:contains('netted hoop')").fadeOut("slow");
	$("span:contains('gridiron')").fadeOut("slow");
	$("span:contains('diamond-shaped')").fadeToggle("slow");
	$("#Basketball").fadeTo("slow", 0.5);
	$("#Football").fadeTo("slow", 0.5);
	$("#Baseball").fadeTo("slow", 1.0);
});

// 3. Functions for MouseOver event - Animate
// Example for type HTML selector
$(":button").mouseover(function() {
	var div = $(this);
	startAnimation();
	function startAnimation() {
		div.animate({
			fontSize : "13px"
		}, "fast");
		div.animate({
			fontSize : "11px"
		}, "fast", startAnimation);
	}
});

// 4. Functions for MouseLeave event - Stop effects with callback
// Example for type HTML selector
$(document).ready(function() {
	$(":button").mouseleave(function() {
		$(this).stop(true);
	});
});

// 5. Function for KeyPress event - Chained 3 fading events
$('html')
		.keypress(
				function(e) {
					if (e.which === 13)
						$("#Wishlist")
								.fadeIn(
										"slow",
										function() {
											document
													.getElementById("Wishmessage").innerHTML = "There are "
													+ (document
															.getElementById("Wishlist").rows.length - 1)
													+ " items you have wished for. Fill the below details to add more items.";
											$("#Wishmessage")
													.fadeIn(
															"slow",
															function() {
																$("#Wishform")
																		.fadeIn(
																				"slow",
																				function() {
																					$(
																							"html, body")
																							.animate(
																									{
																										scrollTop : $(
																												document)
																												.height()
																									},
																									"slow");
																				});
															});
										});
				});

// 6. Function for KeyDown event - Chained 3 animate events
// Example for attribute HTML Selector
$('html').keydown(function(e) {
	if (e.which === 32) {
		$(".CSSTableGenerator").animate({
			marginLeft : "5px"
		}).animate({
			marginTop : "10px"
		});
		$("button").animate({
			paddingLeft : "13px"
		}).animate({
			paddingTop : "11px"
		}).animate({
			paddingBottom : "10px"
		}).animate({
			paddingRight : "16px"
		});
		$("[type]").animate({
			width : "201px"
		}).animate({
			height : "17px"
		});
	}
});

// 7. Function for KeyUp event - Stop chained effects with callback
$('html').keyup(function(e) {
	if (e.which === 32)
		$("html").stop(true, false);
});

// 8. Function for blur event - Get content
$(document).ready(
		function() {
			$("#Name").blur(
					function() {
						document.getElementById("NameHelp").innerHTML = "";
						if ($('#Name').val() != ""
								&& $('#Description').val() != ""
								&& $('#Price').val() != "")
							$('#Add').fadeIn();
						else
							$('#Add').fadeOut();

						$("html, body").animate({
							scrollTop : $(document).height()
						}, "slow");
					});
			$("#Description").blur(
					function() {
						document.getElementById("DescHelp").innerHTML = "";
						if ($('#Name').val() != ""
								&& $('#Description').val() != ""
								&& $('#Price').val() != "")
							$('#Add').fadeIn();
						else
							$('#Add').fadeOut();

						$("html, body").animate({
							scrollTop : $(document).height()
						}, "slow");
					});
			$("#Price").blur(
					function() {
						document.getElementById("PriceHelp").innerHTML = "";
						if ($('#Name').val() != ""
								&& $('#Description').val() != ""
								&& $('#Price').val() != "")
							$('#Add').fadeIn();
						else
							$('#Add').fadeOut();

						$("html, body").animate({
							scrollTop : $(document).height()
						}, "slow");
					});
		});

// 9. Function for change event - Get content
$(document)
		.ready(
				function() {
					$("#Name")
							.change(
									function() {
										if ($('#Name').val() === "")
											alert("Name is empty, you will not be able to add to wishlist");
									});
					$("#Description")
							.change(
									function() {
										if ($('#Description').val() === "")
											alert("Description is empty, you will not be able to add to wishlist");
									});
					$("#Price")
							.change(
									function() {
										if ($('#Price').val() === "")
											alert("Price is empty, you will not be able to add to wishlist");
									});
				});

// 10. Function for focus event - Set content with callback
$(document)
		.ready(
				function() {
					$("#Name")
							.focus(
									function() {
										document.getElementById("NameHelp").innerHTML = "Enter a name for the product";
										$("#NameHelp").css("font-style",
												"italic").fadeIn(
												"slow",
												function() {
													$("#NameHelp").css(
															"margin-left",
															"10px");
												});
									});
					$("#Description")
							.focus(
									function() {
										document.getElementById("DescHelp").innerHTML = "Enter a description for this product";
										$("#DescHelp").css("font-style",
												"italic").fadeIn(
												"slow",
												function() {
													$("#DescHelp").css(
															"margin-left",
															"10px");
												});
									});
					$("#Price")
							.focus(
									function() {
										document.getElementById("PriceHelp").innerHTML = "Enter price you wish for (Dollars)";
										$("#PriceHelp").css("font-style",
												"italic").fadeIn(
												"slow",
												function() {
													$("#PriceHelp").css(
															"margin-left",
															"10px");
												});
									});
				});