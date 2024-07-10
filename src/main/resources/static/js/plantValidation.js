

const APP = {
    init(){

    },
    addListeners(){
        var form = document.getElementById("plantForm")
        var name = document.getElementById("plantName");
        var description = document.getElementById("plantDesc");
        var price = document.getElementById("price");
        var country = document.getElementById("country");
        var city = document.getElementById("city");

        name.addEventListener('onSubmit', APP.test)
    },

    test(){

    }
}