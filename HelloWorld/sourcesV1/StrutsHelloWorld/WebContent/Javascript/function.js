// JavaScript Document

/*
	Checks that the value of the field is an integer (positive or negative)
	Returns 1 if the value is correct, 0 otherwise
	scannedString : the value to test in string type, to reformat
	groupingSeparators : the grouping (thousand) separator  used in the float (for regex use)
	decimalSeparators : the decimal separator  used in the float (for regex use)
	
*/
function com_hello_world_containsInteger(scannedString, decimalSeparators, groupingSeparators){
	//Check that the field contains an integer
	var regexp = new RegExp("^([\-+]?[0-9"+decimalSeparators+groupingSeparators+"]*)([eE][\-+]?)?[0-9]*$");	
	var isCorrectElements =regexp.exec(scannedString);
	if (isCorrectElements !=null){
		var isStrOkForPlusMinus = com_hello_world_checkPlusMinusOnlyValue(scannedString);
		if (isStrOkForPlusMinus == 1){
			//If a decimalSeparator is present : the format is OK only if the exponential symbol is present too
			var indexOfDecimalSeparator = scannedString.indexOf(decimalSeparators);
			if (indexOfDecimalSeparator != -1){
				var lastIndexOfDecimalSeparator = scannedString.lastIndexOf(decimalSeparators);
				if (lastIndexOfDecimalSeparator != indexOfDecimalSeparator){
					//several decimal separators
					return 0 ;
				} else {
					var regexpExp = new RegExp("[eE]");
					var expPosMatch = regexpExp.exec(scannedString);
					if (expPosMatch == null){
						//No exp : shoud not be a decimal separator
						return 0;
					} else {
						//Check the position of the decimal compared to the position of the exponential
						if (indexOfDecimalSeparator>expPosMatch.index){
							return 0;
						}
					}
				}
			}
			
			//Check exponentialSymbolPosition
			var isExponentialSymbolOk = com_hello_world_checkExponentialSymbol(scannedString);
			if(isExponentialSymbolOk == false){
				return 0 ; 
			}
		
			//Check the position of the "-" and the "+" symbol
			var isMinusSymbolOk = com_hello_world_checkMinusSymbol(scannedString);
			if (isMinusSymbolOk == false){
				return 0;
			}
			var regexpMinus = new RegExp("[\-+]");
			var minusPosMatch = regexpMinus.exec(scannedString);
			if (minusPosMatch!=null){
				if (minusPosMatch.index==0){
					scannedString=scannedString.substring(1);
				}				
			}
	
			//Check the thousand separators positions
			var regexpGroupingSep = new RegExp("["+groupingSeparators+"]");
			var groupingPosMatch =regexpGroupingSep.exec(scannedString);
			while (groupingPosMatch !=null){
				var length=scannedString.length;
				var limitSize=5;
				if (groupingSeparators==""){
					limitSize=4;
				}
				if (length<limitSize){
					//Can not have a thousand separator if length < limitSize
					return 0 ;
				}
				
				//Check each position of the thousand separator
				var indexOfFirstGroupingSeparator = groupingPosMatch.index;
				if (indexOfFirstGroupingSeparator != (length % 4)){
					return 0;
				} else {
					scannedString = scannedString.substring(indexOfFirstGroupingSeparator+1);
					groupingPosMatch =regexpGroupingSep.exec(scannedString);
				}
			}	
			return 1;
		} else {
			//Incorrect value : the string conains just + or -
			return 0 ;
		}
	} else {
		//Incorrect value
		return 0 ;
	}
}

/*
	Checks that the value of the field is a long (positive or negative)
	Returns 1 if the value is correct, 0 otherwise
	scannedString : the value to test in string type, to reformat
	groupingSeparators : the grouping (thousand) separator  used in the float (for regex use)
	decimalSeparators : the decimal separator  used in the float (for regex use)
	
*/
function com_hello_world_containsInteger64(scannedString, decimalSeparators, groupingSeparators){
	//Check that the field contains a long
	var regexp = new RegExp("^([\-+]?[0-9"+decimalSeparators+groupingSeparators+"]*)([eE][\-+]?)?[0-9]*$");	
	var isCorrectElements =regexp.exec(scannedString);
	if (isCorrectElements !=null){
		var isStrOkForPlusMinus = com_hello_world_checkPlusMinusOnlyValue(scannedString);
		if (isStrOkForPlusMinus == 1){
			//If a decimalSeparator is present : the format is OK only if the exponential symbol is present too
			var indexOfDecimalSeparator = scannedString.indexOf(decimalSeparators);
			if (indexOfDecimalSeparator != -1){
				var lastIndexOfDecimalSeparator = scannedString.lastIndexOf(decimalSeparators);
				if (lastIndexOfDecimalSeparator != indexOfDecimalSeparator){
					//several decimal separators
					return 0 ;
				} else {
					var regexpExp = new RegExp("[eE]");
					var expPosMatch = regexpExp.exec(scannedString);
					if (expPosMatch == null){
						//No exp : shoud not be a decimal separator
						return 0;
					} else {
						//Check the position of the decimal compared to the position of the exponential
						if (indexOfDecimalSeparator>expPosMatch.index){
							return 0;
						}
					}
				}
			}
			
			//Check exponentialSymbolPosition
			var isExponentialSymbolOk = com_hello_world_checkExponentialSymbol(scannedString);
			if(isExponentialSymbolOk == false){
				return 0 ; 
			}
		
			//Check the position of the "-" and the "+" symbol
			var isMinusSymbolOk = com_hello_world_checkMinusSymbol(scannedString);
			if (isMinusSymbolOk == false){
				return 0;
			}
			var regexpMinus = new RegExp("[\-+]");
			var minusPosMatch = regexpMinus.exec(scannedString);
			if (minusPosMatch!=null){
				if (minusPosMatch.index==0){
					scannedString=scannedString.substring(1);
				}				
			}
	
			//Check the thousand separators positions
			var regexpGroupingSep = new RegExp("["+groupingSeparators+"]");
			var groupingPosMatch =regexpGroupingSep.exec(scannedString);
			while (groupingPosMatch !=null){
				var length=scannedString.length;
				var limitSize=5;
				if (groupingSeparators==""){
					limitSize=4;
				}
				if (length<limitSize){
					//Can not have a thousand separator if length < limitSize
					return 0 ;
				}
				
				//Check each position of the thousand separator
				var indexOfFirstGroupingSeparator = groupingPosMatch.index;
				if (indexOfFirstGroupingSeparator != (length % 4)){
					return 0;
				} else {
					scannedString = scannedString.substring(indexOfFirstGroupingSeparator+1);
					groupingPosMatch =regexpGroupingSep.exec(scannedString);
				}
			}	
			return 1;
		} else {
			//Incorrect value : the string conains just + or -
			return 0 ;
		}
	} else {
		//Incorrect value
		return 0 ;
	}
}

/*
	Returns true if the provided character is found in the tested string ; false otherwise
	stringToTest : the string to test
	forbiddenChar :  the forbidden char
*/

function com_hello_world_containsForbiddenChar(stringToTest, forbiddenChar){
	var regexp = new RegExp("["+forbiddenChar+"]");
	var forbiddenCharPosMatch =regexp.exec(stringToTest);
	//If the forbidden char is not contained in the tested string
	if (forbiddenCharPosMatch == null){
		return false;
	} else {
		return true;
	}
}


/*
	Returns true if only the provided character are found in the tested string ; false otherwise
	stringToTest : the string to test
	forbiddenChar :  the authorized char
*/
function com_hello_world_containsOnlyAuthorizedListCharac(stringToTest, authorizedChar){
	
	var regexp = new RegExp("[^"+authorizedChar+"]");
	var forbiddenCharPosMatch =regexp.exec(stringToTest);
	//If the forbidden char is not contained in the tested string
	if (forbiddenCharPosMatch == null){
		return true;
		
	} else {
		return false;
	}
	

}

/*
	Tests if the value to test is a float.
	Returns 1 if the value is a correct float, 0 othewise.
	valueToTest : the value to test in string type, to reformat
	groupingSeparators : the grouping (thousand) separator  used in the float (for regex use)
	decimalSeparators : the decimal separator  used in the float (for regex use)

*/
function com_hello_world_containsFloat(valueToTest, decimalSeparators, groupingSeparators){
	var scannedString = valueToTest;
	var floatLength=scannedString.length;
	
	//Check that the scannedString contains float characters only
	var regexp = new RegExp("^([\-+]?[0-9"+decimalSeparators+groupingSeparators+"]*)([eE][\-+]?)?[0-9]*$");	
	
	var isCorrectElements =regexp.exec(scannedString);
	
	//If contains only authorized characters
	if (isCorrectElements != null){
		var isStrOkForPlusMinus = com_hello_world_checkPlusMinusOnlyValue(scannedString);
		if (isStrOkForPlusMinus == 1){
	
	
			//Check exponentialSymbolPosition
			var isExponentialSymbolOk = com_hello_world_checkExponentialSymbol(scannedString);
			if(isExponentialSymbolOk == false){
				return 0 ; 
			}
			
			//Check the position of the "-" symbol
			var isMinusSymbolOk = com_hello_world_checkMinusSymbol(scannedString);
			if (isMinusSymbolOk == false){
				return 0;
			}
			var regexpMinus = new RegExp("[\-+]");
			var minusPosMatch = regexpMinus.exec(scannedString);			
			if (minusPosMatch!=null){
				if (minusPosMatch.index==0){
					scannedString=scannedString.substring(1);
				}
			}
			
			//Test for the position of the decimal separator
			var regexpDecimalSep = new RegExp("["+decimalSeparators+"]");
			var decimalPosMatch =regexpDecimalSep.exec(scannedString);
			if (decimalPosMatch !=null){
				var indexForDecimalSymbol = decimalPosMatch.index;
				//Check the unicity of the decimal symbol		
				var indexForLastDecimalSymbol = scannedString.lastIndexOf(decimalSeparators);
				if (indexForLastDecimalSymbol != indexForDecimalSymbol){
					return 0;
				}			
				//Check the position of the decimal separator : neither at the beginning nor at the end of the string
				if (indexForDecimalSymbol==0 || indexForDecimalSymbol==(floatLength-1)){
					return 0;
				}
				
				//Check that no thousand seperator is located after the decimal separator
				var regexpGroupingSep = new RegExp("["+groupingSeparators+"]");
				var groupingPosMatch =regexpGroupingSep.exec(scannedString);
				if (groupingPosMatch!=null){
					if (groupingPosMatch.index>indexForDecimalSymbol){
						return 0;
					}
				}
				//Take only the int part of the float
				scannedString = scannedString.substring(0,indexForDecimalSymbol); 
			}
			
			
			//Test for the position of the thousand separator in the int part of the float
			var regexpGroupingSep = new RegExp("["+groupingSeparators+"]");
			var groupingPosMatch =regexpGroupingSep.exec(scannedString);
			while (groupingPosMatch !=null){
				var intPartLength=scannedString.length;
				var limitSize=5;
				if (groupingSeparators==""){
					limitSize=4;
				}
				if (intPartLength<limitSize){
					//Can not have a thousand separator if length < limitSize
					return 0 ;
				}
				
				//Check each position of the thousand separator
				var indexOfFirstGroupingSeparator = groupingPosMatch.index;
				if (indexOfFirstGroupingSeparator != (intPartLength % 4)){
					return 0;
				} else {
					scannedString = scannedString.substring(indexOfFirstGroupingSeparator+1);
					
					groupingPosMatch =regexpGroupingSep.exec(scannedString);
				}
			}	
			return 1;
		} else {
			//The string contains only "+" or "-"
			return 0;
		}
	} else {
		return 0;
	}
} 


/*
	Reformat a float into the language specific formatted string, the decimal separator symbol, and the thousand separator symbol
	floatString : the float in string type, to reformat
	groupingSeparators : the grouping (thousand) separator to use in the float 
	decimalSeparators : the decimal separator to use in the float
	
*/

function com_hello_world_formatFloat(floatString, decimalSeparators, groupingSeparators){
	groupingSeparators = new String(groupingSeparators);
	var formattedFloat = floatString;

	//Replaces the "." symbol with the decimal separator 
	var regexpDecimalSep = new RegExp("[\.]");
	var decimalPosMatch =regexpDecimalSep.exec(formattedFloat);

	var intPart =formattedFloat;
	var floatPart="";
	if (decimalPosMatch !=null){
		var decimalPosMatchIndex = decimalPosMatch.index;
		formattedFloat =formattedFloat.toString().substring(0,decimalPosMatchIndex)+decimalSeparators+formattedFloat.toString().substring(decimalPosMatchIndex+1,formattedFloat.length);
		intPart= formattedFloat.toString().substring(0,decimalPosMatchIndex);
		floatPart =formattedFloat.toString().substring(decimalPosMatchIndex,formattedFloat.length);
	}
	
	//Format the integer part of the float
	var formattedIntPart = com_hello_world_formatInteger(new String(intPart),groupingSeparators);
	return (formattedIntPart+floatPart);
	
}



/*	
	Reformat an integer into the language specific formatted string, and the thousand separator symbol
	intString : the integer in string type, to reformat
	groupingSeparators : the grouping (thousand) separator to use in the integer 
*/
function com_hello_world_formatInteger(intString, groupingSeparators){
	intString = new String(intString);
	var formattedInt = "";
	var firstCharac = intString.indexOf(0);
	var minusSymbol="";
	if (firstCharac == "-"){
		minusSymbol="-";
		intString = intString.substring(1);
	}
	
	//Adds the thousand separator symbols
	var intStringLength = intString.length;
	if (intStringLength<=3){
		return (minusSymbol+intString);
	}
	while (intString.length>3){
		var rigthPart= intString.substring(intString.length-3);
		var leftPart= intString.substring(0,intString.length-3);
		formattedInt =groupingSeparators+rigthPart +formattedInt;
		if (leftPart.length<=3){
			formattedInt = minusSymbol+leftPart+formattedInt;
		}
		intString = leftPart;
	}
	return formattedInt;
}

/*	
	Reformat a long into the language specific formatted string, and the thousand separator symbol
	longString : the long in string type, to reformat
	groupingSeparators : the grouping (thousand) separator to use in the integer 
*/
function com_hello_world_formatInteger64(longString, groupingSeparators){
	longString = new String(longString);
	var formattedLong = "";
	var firstCharac = longString.indexOf(0);
	var minusSymbol="";
	if (firstCharac == "-"){
		minusSymbol="-";
		longString = longString.substring(1);
	}
	
	//Adds the thousand separator symbols
	var longStringLength = longString.length;
	if (longStringLength<=3){
		return (minusSymbol+longString);
	}
	while (longString.length>3){
		var rigthPart= longString.substring(longString.length-3);
		var leftPart= longString.substring(0,longString.length-3);
		formattedLong =groupingSeparators+rigthPart +formattedLong;
		if (leftPart.length<=3){
			formattedLong = minusSymbol+leftPart+formattedLong;
		}
		longString = leftPart;
	}
	return formattedLong;
}


/*	Reformat a correct float from the language specific formatted string, the decimal separator symbol, and the thousand separator symbol
	floatString : the float in string type, to reformat
	decimalSeparatorsForRegex : the decimal separator used in the float (for regex use)
	groupingSeparatorsForRegex : the grouping (thousand) separator  used in the float (for regex use)
*/

function com_hello_world_unformatFloat(floatString, decimalSeparatorsForRegex, groupingSeparatorsForRegex){
	var unformattedFloat = floatString;
	//Check that the float string is correct
	var isContentCorrect = com_hello_world_containsFloat(floatString, decimalSeparatorsForRegex, groupingSeparatorsForRegex);
	if (isContentCorrect==0){
		return unformattedFloat;
	} else {
		//Replaces the decimal separator with the "." symbol
		var regexpDecimalSep = new RegExp("["+decimalSeparatorsForRegex+"]");
		var decimalPosMatch =regexpDecimalSep.exec(unformattedFloat);
		
		var intPart =unformattedFloat;
		var floatPart="";
		if (decimalPosMatch !=null){
			var decimalPosMatchIndex = decimalPosMatch.index;
			unformattedFloat =unformattedFloat.substring(0,decimalPosMatchIndex)+"."+unformattedFloat.substring(decimalPosMatchIndex+1);
			intPart= unformattedFloat.substring(0,decimalPosMatchIndex);
			floatPart =unformattedFloat.substring(decimalPosMatchIndex,unformattedFloat.length);
		}
		//Unformat the integer part of the float
		var unformattedIntPart = com_hello_world_unformatInteger(intPart,groupingSeparatorsForRegex);
		return (unformattedIntPart+floatPart);
	}
}


/* 	Reformats a correct integer from the language specific formatted string, and the thousand separator symbol
	intString : the integer in string type, to reformat
	groupingSeparatorsForRegex : the grouping (thousand) separator  used in the integer (for regex use)
*/
function com_hello_world_unformatInteger(intString, groupingSeparatorsForRegex){
	var unformattedInt = intString;
	//Removes the thousand separator symbols
	var regexpGroupingSep = new RegExp("["+groupingSeparatorsForRegex+"]");
	var groupingPosMatch =regexpGroupingSep.exec(unformattedInt);
	while (groupingPosMatch !=null){
		var indexOfFirstGroupingSeparator=groupingPosMatch.index;
		unformattedInt =unformattedInt.substring(0,indexOfFirstGroupingSeparator)+unformattedInt.substring(indexOfFirstGroupingSeparator+1);
		groupingPosMatch =regexpGroupingSep.exec(unformattedInt);
	}
	return unformattedInt;
}

/* 	Reformats a correct long from the language specific formatted string, and the thousand separator symbol
	longString : the long in string type, to reformat
	groupingSeparatorsForRegex : the grouping (thousand) separator  used in the integer (for regex use)
*/
function com_hello_world_unformatInteger64(longString, groupingSeparatorsForRegex){
	var unformattedLong = longString;
	//Removes the thousand separator symbols
	var regexpGroupingSep = new RegExp("["+groupingSeparatorsForRegex+"]");
	var groupingPosMatch =regexpGroupingSep.exec(unformattedLong);
	while (groupingPosMatch !=null){
		var indexOfFirstGroupingSeparator=groupingPosMatch.index;
		unformattedLong =unformattedLong.substring(0,indexOfFirstGroupingSeparator)+unformattedLong.substring(indexOfFirstGroupingSeparator+1);
		groupingPosMatch =regexpGroupingSep.exec(unformattedLong);
	}
	return unformattedLong;
}

/* 
	Removes blank characters at the end and at the begginning of the provide string, and returns the new value.
	val : the string to trim
*/
function com_hello_world_trim(val) {
	 var deb = 0;
	 var fin = val.length - 1;
	 while ( (val.charAt(deb) == " ") && (deb < val.length) ) {
		 deb++;
	 }
	 while ( (val.charAt(fin) == " ") && (fin > deb) ) {
		 fin--;
	 }
	 return val.substring(deb,fin+1);
}


/* 	
	Checks the position(s) of the exponent (e or E) symbol in the provided string.
	Returns 1 if the string is ok v/s the symbol ; 0 otherwise.
	scannedString : the string to test
*/
function com_hello_world_checkExponentialSymbol(scannedString){
	//Check the exponential symbol
	var regexpExp = new RegExp("[eE]");
	var expPosMatch = regexpExp.exec(scannedString);
	if (expPosMatch != null){
		//There is an exponential symbol
		var firstPosOfSmallE = scannedString.indexOf("e");
		var lastPosOfSmallE = scannedString.lastIndexOf("e");
		var firstPosOfBigE = scannedString.indexOf("E");
		var lastPosOfBigE = scannedString.lastIndexOf("E");
		if(firstPosOfSmallE !=-1){
			if (firstPosOfSmallE != lastPosOfSmallE || firstPosOfBigE !=-1){
				return 0;
			} else {
				return 1 ;
			}
		}
		if(firstPosOfBigE !=-1){
			if (firstPosOfBigE != lastPosOfBigE || firstPosOfSmallE !=-1){
				return 0;
			} else {
				return 1 ;
			}
		}
	}
	return 1 ;
}

/* 	
	Checks the position(s) of the minus symbol in the provided string.
	Returns 1 if the string is ok v/s the minus symbol ; 0 otherwise.
	scannedString : the string to test
	isMinusAsFirstCharacAuthorized : true if the minus characted is authorized as first character. If null, uses the "true" value as default.
*/
function com_hello_world_checkMinusSymbol(scannedString, isMinusAsFirstCharacAuthorized){
	if (isMinusAsFirstCharacAuthorized == null){
		isMinusAsFirstCharacAuthorized = true;
	}
	//Check the position of the "-" symbol
	var regexpMinus = new RegExp("[-]");
	var minusPosMatch = regexpMinus.exec(scannedString);
	
	if (minusPosMatch!=null){
		if (isMinusAsFirstCharacAuthorized == true){
			if (minusPosMatch.index!=0){
				//The "-" symbol is not in first position 
				//Check if the "-" symbol is about the exponential symbol
				var regexpExp = new RegExp("[eE]");
				var expPosMatch = regexpExp.exec(scannedString);
				if (expPosMatch == null){
					return 0 ; 
				} else {	
					if (expPosMatch.index!=(minusPosMatch.index-1)){
						return 0 ;
					}
				}
			} else {
				//Check if there is at least one digit after the minus symbol
				if(scannedString.length==1){
					return 0;
				}
			}
		} else {
			if (minusPosMatch.index==0){
				return 0 ;
			} else {
				//Check if the "-" symbol is about the exponential symbol
				var regexpExp = new RegExp("[eE]");
				var expPosMatch = regexpExp.exec(scannedString);
				if (expPosMatch == null){
					return 0 ;
				} else {	
					//alert(" minusPosMatch!=null and isMinusAsFirstCharacAuthorized == false\n and minusPosMatch.index="+minusPosMatch.index+" and expPosMatch.index:"+expPosMatch.index);
					var expPosMatchNumberValue = new Number(expPosMatch.index).valueOf();
					var minusPosMatchNumberValue = new Number(minusPosMatch.index).valueOf();
					if ((expPosMatchNumberValue != (minusPosMatchNumberValue-1)) == true){
						return 0 ;
					}
				}
			}
		}
		//Check if after the minus symbol following the exp symbol, there are other minus symbols
		isMinusAsFirstCharacAuthorized = false;
		return com_hello_world_checkMinusSymbol(scannedString.substring((minusPosMatch.index+1),scannedString.length),isMinusAsFirstCharacAuthorized);
	} else {
		return 1;
	}
}

/* 	Changes the aspect of the cursor to a hand shape
	obj : the object which style is to be changed
	cursor : the cursor to use. If null, use the "hand" cursor as default.
*/
function com_hello_world_changeToHandCursor(obj, cursor){
	if(cursor == null){
		cursor="hand";
	}
	obj.style.cursor=cursor;
}
/*
	Checks that the provided value does not contain just "+" or "-" (for integers and float fields)
	If it does, returns 0; otherwise returns 1
*/
function com_hello_world_checkPlusMinusOnlyValue(scannedString){
	//If only one character in the string, and the character equals "+" or "-", returns 0
	if(scannedString.length==1){
		if(scannedString.charAt(0) == "+" || scannedString.charAt(0) == "-"){
			return 0;
		}
	}
	return 1;
}
