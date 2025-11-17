console.log("admin page")

document.querySelector("#image_input")
        .addEventListener("change", function(event)
        {
            let file = event.target.files[0];
            let reader = new FileReader();
            reader.onload = function() {
                document
                .querySelector("#Preview_image")
                .setAttribute("src",reader.result);
            };
            reader.readAsDataURL(file);
        });