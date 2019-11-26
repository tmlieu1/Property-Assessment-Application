/* CMPT 305 Milestone 3
*  Edmonton Properties assessment 
*/


main();

function main(){
    var json = document.getElementById("PropertyAssessmentGUI");
    console.log(json)
    var latitudeList = getDataFromJava(document.getElementById("PropertyAssessmentGUI"));
    console.log(latitudeList);
}


/**
 * 
 * @param {List of Properties} jsonName 
 */
function getDataFromJava(jsonName){
    if(typeof jsonName == "undefined"){
        printError("JSON name has not been defined.");
    }
    var dataList = [];
    for(let i = 0; i < jsonName.length; i++){
        dataList.push(jsonName[i].latitude);
        console.log(jsonName[i]);
    }
    return dataList;
}