/* CMPT 305 Milestone 3
*  Edmonton Properties assessment 
*/


main();

function main(){
    // var javaFXClass = document.getElementById("PropertyAssessmentGUI");
    // var theJson = javaFXClass.getPropertyAPI("https://data.edmonton.ca/resource/q7d6-ambg.json");
    // console.log(theJson);
    var request = new XMLHttpRequest();
    //var javafx = new PropertyAssessmentGUI();
    var latitudeList = [];
    var longitudeList = [];
    var assessmentList = [];
    request.open('GET', 'https://data.edmonton.ca/resource/q7d6-ambg.json');
    
    request.onload = function(){
        var data = JSON.parse(this.response);

        if(request.status > 199 && request.status < 400){
            data.forEach(element => {
                latitudeList.push(element.latitude);
                longitudeList.push(element.longitude);
                assessmentList.push(element.total_asmt);
                
            });
        }
        else{
            console.error("Error the request for the API could not be made.");
        }

        

    };

    request.send()
}


// /**
//  * 
//  * @param {List of Properties} jsonName 
//  */
// function getDataFromJava(jsonName){
//     // if(typeof jsonName == "undefined"){
//     //     printError("JSON name has not been defined.");
//     // }
//     // var dataList = [];
//     // for(let i = 0; i < jsonName.length; i++){
//     //     dataList.push(jsonName[i].latitude);
//     //     console.log(jsonName[i]);
//     // }
//     return dataList;
// }