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
    
    var heatData = [];
    
    var edmonton = {lat: 53.5461, lng: -113.4938};
    request.open('GET', 'https://data.edmonton.ca/resource/q7d6-ambg.json');
    
    // fetch('../../../../Properties.json')
    //     .then((data) =>{
    //         return jsonData = data.json();
    //     })
    //     .then((jsonData) =>{
    //         var updatedJsonData = JSON.parse(jsonData);

    //         updatedJsonData.forEach(element => {
    //             assessmentList.push(element.total_asmt);
    //             var latLong = new google.maps.LatLng(element.latitude, element.longitude);
    //             heatData.push(latLong);
    //         });

    //         var maps = new google.maps.Map(document.getElementById('map'), {
    //             zoom:11.5,
    //             center: edmonton,
    //             mapTypeId: 'terrain'
    //         });
            
    //         var script = document.createElement('script');
    
    //         var heatMap = new google.maps.visualization.HeatmapLayer({
    //             data: heatData,
    //             dissipating: false,
    //             map: maps
    //         })
    //     //    for(var i = 0; i < updatedJsonData.length; i++){

    //     //    }
            
    //     }).catch((problem) => {
    //         console.error(problem);
    //     });

    request.onload = function(){
        var data = JSON.parse(this.response);

        if(request.status > 199 && request.status < 400){
            data.forEach(element => {
                // latitudeList.push(element.latitude);
                // longitudeList.push(element.longitude);
                // assessmentList.push(element.total_asmt);

                var latLong = new google.maps.LatLng(element.latitude, element.longitude);
                heatData.push(latLong);
            });
        }
        else{
            console.error("Error the request for the API could not be made.");
        }

        var maps = new google.maps.Map(document.getElementById('map'), {
            zoom:11.5,
            center: edmonton,
            mapTypeId: 'terrain'
        });

        var script = document.createElement('script');

        var heatMap = new google.maps.visualization.HeatmapLayer({
            data: heatData,
            dissipating: false,
            map: maps
        })
        

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