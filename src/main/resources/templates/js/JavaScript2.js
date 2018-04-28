
var myIndex = 0;
var myIndex2 = 0;
var myIndex3 = 0;

function myfunction()
{

carousel();
carousel2();
carousel3();

}
function carousel() {
    var i;
    var x = document.getElementsByClassName("mySlides");
    for (i = 0; i < x.length; i++) {
       x[i].style.display = "none";
    }
    myIndex++;
    if (myIndex > x.length) {myIndex = 1}
    x[myIndex-1].style.display = "block";
    setTimeout(carousel, 2000); // Change image every 2 seconds
}



function carousel2() {
    var i;
    var x = document.getElementsByClassName("mySlides2");
    for (i = 0; i < x.length; i++) {
       x[i].style.display = "none";
    }
    myIndex2++;
    if (myIndex2 > x.length) {myIndex2 = 1}
    x[myIndex2-1].style.display = "block";
    setTimeout(carousel2, 2000); // Change image every 2 seconds
}

function carousel3() {
    var i;
    var x = document.getElementsByClassName("mySlides3");
    for (i = 0; i < x.length; i++) {
       x[i].style.display = "none";
    }
    myIndex3++;
    if (myIndex3 > x.length) {myIndex3 = 1}
    x[myIndex3-1].style.display = "block";
    setTimeout(carousel3, 2000); // Change image every 2 seconds
}
