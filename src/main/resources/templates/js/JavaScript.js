
function validateSignUpForm()
{
	
	//check for user name
	    var flag=true;
	    var n = document.forms["signUpForm"]["uname"].value;
		
	
        var l = n.length;
        
        if (l == 0) {
			
			document.getElementById("error1").innerHTML="ERROR: Please fill 'User Name' Field";
			
			flag = false;
       }
       else {
			for (var i = 0; i < l; i++) {
				if (n[i] >= 'A' && n[i] <= 'Z' || n[i] >= 'a' && n[i] <= 'z' || n[i] == ' ' || n[i] >= '0' && n[i] <= '9')
				{document.getElementById("error1").innerHTML="";
					}
				else {
				   document.getElementById("error1").innerHTML="ERROR: Use Only Upper Lower Case Letters in 'User Name' Field";
					flag = false;
					break;
				}
			}
    }

	//check for email
	
	
	n = document.forms["signUpForm"]["email"].value;
    l = n.length;
    if (l == 0) {
        
		document.getElementById("error2").innerHTML="ERROR: Please fill 'Email Adress' Field";
       
        flag = false;
    }
  /*  else if (n.indexOf("@") == -1 && l != 0) { 
	          
			  flag = false;
              document.getElementById("error2").innerHTML="ERROR: Please enter correct email";
       			  
	}

   else if (l > 4) {
        if (n[l - 1] == 'm' && n[l - 2] == 'o' && n[l - 3] == 'c' && n[l - 4] == '.' && n.indexOf("@") - 1 >= 0)
        {
			document.getElementById("error2").innerHTML="";
             
		}
		else {
             flag = false;
			 document.getElementById("error2").innerHTML="ERROR: Please enter correct email";
               
        }

    }
    */
	
	
	//for Country
	n = document.forms["signUpForm"]["city"].value;
    l = n.length;
    if (l == 0) {
       
		document.getElementById("error3").innerHTML="ERROR: Please fill 'Country'  Field";
        
        flag = false;
    }
    else {
        for (var i = 0; i < l; i++) {
            if (n[i] >= 'a' && n[i] <= 'z')
			{ document.getElementById("error3").innerHTML="";
        }
            else {
               document.getElementById("error3").innerHTML="ERROR: Use Only Lower Case Letters in 'City' Field";
        
                flag = false;
				break;
            }
        }
    }
    
	
	//checks for confirm password and password
    var d = document.forms["signUpForm"]["psw1"].value;
    n = document.forms["signUpForm"]["psw2"].value;
    l = d.length;
    var m=n.length;
   
    if(m==0)
	{
		document.getElementById("error5").innerHTML="ERROR: Please Fill 'Confirm Password' Field";
        
        flag = false;
		
	}	
    else{
		document.getElementById("error5").innerHTML="";
       
	}	
	
    if (l == 0) {
       
       document.getElementById("error4").innerHTML="ERROR: Please Fill 'Password' Field";
        
        flag = false;
    }
	else{
		document.getElementById("error4").innerHTML="";
        
	}
    if (n.localeCompare(d) != 0 && l != 0 && m != 0) {
       
		document.getElementById("error5").innerHTML="ERROR: 'Confirm Password' does not match with 'Password'";
        
        flag = false;
    }
    else if(m != 0){
		document.getElementById("error5").innerHTML="";
       
	}

	 //checks for Phone Number
    n = document.forms["signUpForm"]["phone"].value;
    l = n.length;
    if (l == 0) {
        document.getElementById("error6").innerHTML="ERROR: Please fill 'PhoneNumber' Field";
        
        flag = false;
    }
    else {
        for (var i = 0; i < 12; i++) {
            if (((n[i] >= 0 && n[i] <= 9 ) || (n[4] == '-')) && l == 12 )
			{
				 document.getElementById("error6").innerHTML="";
				
			}
			
            else {
                 document.getElementById("error6").innerHTML="ERROR: PhoneNumber should be in correct format";
                flag = false;
				break;
            }
        }
    }
   
	
	return flag;    
    
	
}


function contactCheck()
{
	//check for user name
	    var flag=true;
	    var n = document.forms["contactForm"]["uname"].value;
		
	
        var l = n.length;
        
        if (l == 0) {
			
			document.getElementById("contactError1").innerHTML="ERROR: Please fill 'User Name' Field";
			
			flag = false;
       }
       else {
			for (var i = 0; i < l; i++) {
				if (n[i] >= 'A' && n[i] <= 'Z' || n[i] >= 'a' && n[i] <= 'z' || n[i] == ' ')
				{
					  document.getElementById("contactError1").innerHTML="";
				
				}
				else {
				   document.getElementById("contactError1").innerHTML="ERROR: Use Only Upper Lower Case Letters in 'User Name' Field";
					flag = false;
					break;
				}
			}
    }

	
	//check for email
	
	
	n = document.forms["contactForm"]["email"].value;
    l = n.length;
    if (l == 0) {
        
		document.getElementById("contactError2").innerHTML="ERROR: Please fill 'Email Adress' Field";
       
        flag = false;
    }
/*    else if (n.indexOf("@") == -1 && l != 0) { 
	          
			  flag = false;
              document.getElementById("contactError2").innerHTML="ERROR: Please enter correct email";
       			  
	}

    else if (l > 4) {
        if (n[l - 1] == 'm' && n[l - 2] == 'o' && n[l - 3] == 'c' && n[l - 4] == '.' && n.indexOf("@") - 1 >= 0)
		{
			document.getElementById("contactError2").innerHTML="";
             
		}
        else {
             flag = false;
			 document.getElementById("contactError2").innerHTML="ERROR: Please enter correct email";
            
        }

    }*/
    //check for subject
	    
	    var n = document.forms["contactForm"]["subject"].value;
		
	
        var l = n.length;
        
        if (l == 0) {
			
			document.getElementById("contactError3").innerHTML="ERROR: Please fill 'Subject' Field";
			
			flag = false;
       }
       else {
			for (var i = 0; i < l; i++) {
				if (n[i] >= 'A' && n[i] <= 'Z' || n[i] >= 'a' && n[i] <= 'z' || n[i] == ' ')
				{
					  document.getElementById("contactError3").innerHTML="";
					
				}
				else {
				   document.getElementById("contactError3").innerHTML="ERROR: Use Only Upper Lower Case Letters in 'Subject' Field";
					flag = false;
					break;
				}
			}
    }
	
	
	//check for message
	    
	    var n = document.forms["contactForm"]["message"].value;
		
	
        l = n.length;
        
        if (l == 0) {
			
			document.getElementById("contactError4").innerHTML="ERROR: Please fill 'Mesage' Field";
			
			flag = false;
       }
	   else{
		   document.getElementById("contactError4").innerHTML="";
			
		   
	   }
		
	return flag;    
    
}
function reserveCheck()
{
	
    var x;
	var flag=true;
	
	
	//check for room type
	var e = document.getElementById("roomType");
    x = e.options[e.selectedIndex].value;
	if(x == 1)
	{
		document.getElementById("bookError1").innerHTML="ERROR: Please select room type";
		flag=false;
		
	}
	else{
		
		document.getElementById("bookError1").innerHTML="";
		
	}

	
	//check for category
  
     e = document.getElementById("category");
    x = e.options[e.selectedIndex].value;
	if(x == 1)
	{
		document.getElementById("bookError2").innerHTML="ERROR: Please select category";
		flag=false;
		
	}
	else{
		
		document.getElementById("bookError2").innerHTML="";
		
	}

	
	
    // Get the value of the input field with id="numb"
    x = document.forms["reserveForm"]["room"].value;

    // If x is Not a Number or less than one or greater than 10
	if(x.length == 0)
	{
		document.getElementById("bookError3").innerHTML="ERROR: Please fill 'No. Of Rooms' Field";
		flag=false;
	}
	
    else if (isNaN(x) || x < 1) {
       
		document.getElementById("bookError3").innerHTML="ERROR: Input not valid";
	    flag=false;		
    }
    else if(x > 10)
    {
           document.getElementById("bookError3").innerHTML="ERROR: You can not book more than 10 rooms";
			flag=false;
    }	
	else {
         document.getElementById("bookError3").innerHTML="";
		
    }
    
    return flag;
}







function updateCheck()
{
	var x;
	var flag=true;
	
	
	//check for room type
	var e = document.getElementById("roomTypeUpdate");
    x = e.options[e.selectedIndex].value;
	if(x == 1)
	{
		document.getElementById("updateError1").innerHTML="ERROR: Please select room type";
		flag=false;
		
	}
	else{
		
		document.getElementById("updateError1").innerHTML="";
		
	}

	
	//check for category
  
     e = document.getElementById("categoryUpdate");
    x = e.options[e.selectedIndex].value;
	if(x == 1)
	{
		document.getElementById("updateError2").innerHTML="ERROR: Please select category";
		flag=false;
		
	}
	else{
		
		document.getElementById("updateError2").innerHTML="";
		
	}

	
	
    // Get the value of the input field with id="numb"
    x = document.forms["MupdateForm"]["roomPrice"].value;

    // If x is Not a Number or less than one or greater than 10
	if(x.length == 0)
	{
		document.getElementById("updateError3").innerHTML="ERROR: Please fill 'Price' Field";
		flag=false;
	}
	
    else if (isNaN(x) || x < 1) {
       
		document.getElementById("updateError3").innerHTML="ERROR: Input not valid";
	    flag=false;		
    }
    else if(x > 10000)
    {
           document.getElementById("updateError3").innerHTML="ERROR: You can not set price more than 10,000 ";
			flag=false;
    }	
	else {
         document.getElementById("updateError3").innerHTML="";
		
    }
    
    return flag;
	
	
}

function updateProfile(x)
{
    if(x.localeCompare('mainlogin2')==0)
	{
		 document.getElementById('mainlogin7').style.display = 'none';
		 document.getElementById('mainlogin3').style.display = 'none';
	     document.getElementById('mainlogin4').style.display = 'none';
	  
	     document.getElementById(x).style.display = 'block';
	   
	
	}
	else if(x.localeCompare('mainlogin3')==0)
	{
		
		 document.getElementById('mainlogin2').style.display = 'none';
	    document.getElementById('mainlogin4').style.display = 'none';
	  document.getElementById('mainlogin7').style.display = 'none';
	    document.getElementById(x).style.display = 'block';
	   
	
	}
	else if(x.localeCompare('mainlogin4')==0)
	{
		 document.getElementById('mainlogin2').style.display = 'none';
	   document.getElementById('mainlogin3').style.display = 'none';
	     document.getElementById('mainlogin7').style.display = 'none';
	  
	   document.getElementById(x).style.display = 'block';
	   
	
	}
	else if(x.localeCompare('mainlogin7')==0)
	{
		 document.getElementById('mainlogin2').style.display = 'none';
	   document.getElementById('mainlogin3').style.display = 'none';
	    document.getElementById('mainlogin4').style.display = 'none';
	  
	   document.getElementById(x).style.display = 'block';
	   
	
	}
	else if(x.localeCompare('mainlogin5')==0)
	{
		
	   document.getElementById(x).style.display = 'block';
	   
	
	}
	else if(x.localeCompare('mainlogin6')==0)
	{
		
	   document.getElementById('mainlogin5').style.display = 'none';
	   
	
	}
	else{
		
	}
	
}

function changeUser()
{
	
	  var flag=true;
	    var n = document.forms["changeUserNameForm"]["userName"].value;
		
	
        var l = n.length;
        
        if (l == 0) {
			
			document.getElementById("changeUserError1").innerHTML="ERROR: Please fill 'User Name' Field";
			
			flag = false;
       }
       else {
			for (var i = 0; i < l; i++) {
				if (n[i] >= 'A' && n[i] <= 'Z' || n[i] >= 'a' && n[i] <= 'z' || n[i] == ' ')
				{
					  document.getElementById("changeUserError1").innerHTML="";
				
				}
				else {
				   document.getElementById("changeUserError1").innerHTML="ERROR: Use Only Upper Lower Case Letters in 'User Name' Field";
					flag = false;
					break;
				}
			}
    }
	
	return flag;
}
function emailChange()
{
	var flag=true;
	
	var n = document.forms["emailChangeForm"]["emailChange1"].value;
    var l = n.length;
    if (l == 0) {
        
		document.getElementById("emailChangeError1").innerHTML="ERROR: Please fill 'Email Adress' Field";
       
        flag = false;
    }
    else if (n.indexOf("@") == -1 && l != 0) { 
	          
			  flag = false;
              document.getElementById("emailChangeError1").innerHTML="ERROR: Please enter correct email";
       			  
	}

    else if (l > 4) {
        if (n[l - 1] == 'm' && n[l - 2] == 'o' && n[l - 3] == 'c' && n[l - 4] == '.' && n.indexOf("@") - 1 >= 0)
		{
			document.getElementById("emailChangeError1").innerHTML="";
             
		}
        else {
             flag = false;
			 document.getElementById("emailChangeError1").innerHTML="ERROR: Please enter correct email";
            
        }

    }
	
	
	return flag;
}