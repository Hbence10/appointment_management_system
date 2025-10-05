const ruleParagraph = document.getElementById("ruleText")
const colorInput = document.getElementById("wantedColor")
const headerSelect = document.getElementById("headerSelect")  

async function getRules(){
    const apiCall = (await fetch("http://localhost:8080/rule")).json()
    const apiData = (await apiCall)

    console.log(apiData)
    ruleParagraph.innerHTML = apiData.text
}

function editSelectedText(styleType) {
    const selectedText = window.getSelection()
    const parentHTMLElement = selectedText.getRangeAt(0)

    document.execCommand("bold", true)
}

document.addEventListener("DOMContentLoaded", () => {
    getRules()
})