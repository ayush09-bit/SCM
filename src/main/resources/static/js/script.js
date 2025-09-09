console.log("script loaded")

let currentTheme=gettheme();

changetheme(currentTheme);
//main theme
function changetheme()
{
     document.querySelector("html").classList.add(currentTheme);

    const changethemebutton =  document.querySelector('#theme');
    changethemebutton.addEventListener("click",(event)=>
    {
        console.log("change theme button clicked");
        const oldtheme = currentTheme;

        //remove the current theme
        document.querySelector('html').classList.remove(currentTheme);

        if(currentTheme == "dark") currentTheme = "light";
        else currentTheme = "dark";
        //local storage updated
        settheme(currentTheme);
        // set the current theme
        document.querySelector('html').classList.add(currentTheme);

        changethemebutton.querySelector("span").textContent = oldtheme;
    }
    )

}

//set theme to local storage
function settheme(theme)
{
    localStorage.setItem("theme",theme);

}


//get theme to local storage
function gettheme()
{
    let theme = localStorage.getItem("theme");
    if(theme) return theme;
    else return "dark";

}