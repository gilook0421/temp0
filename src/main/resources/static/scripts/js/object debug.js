console.log("window : " + window);
console.log("window key : " + Object.keys(window));
console.log("window val : " + Object.values(window));

for(var key in window){
    console.log(`${key}` + "=>" + `${window[key]}`);
}