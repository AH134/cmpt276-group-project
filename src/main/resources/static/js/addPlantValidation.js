
document.getElementById("plant-form").addEventListener("submit", () => {
    //var name = document.getElementById("plantName");
    //var desc = document.getElementById("plantDesc");
    //var price = document.getElementById("price");
    //var province
    //var city

})

document.getElementById("province").addEventListener("change", () =>{
    var provinces = ["AB", "BC", "MB", "NB", "NL", "NS", "ON", "PE", "QC", "SK", "NT", "NU", "YT"]
    var curProvince = document.getElementById("province").value;

    //hide the old city select
    provinces.forEach((p)=>{
        var province = document.getElementById(p);

        if (province.classList.contains("d-flex")){
            province.classList.remove("d-flex")
            province.classList.add("d-none")
        }
    })

    //select the current city select
    var curcities = document.getElementById(curProvince)

    //remove the invisible display class and make it visible
    curcities.classList.remove("d-none")
    curcities.classList.add("d-flex")
    
})