const ruleParagraph = document.getElementById("ruleText")

async function getRules(){
    const apiCall = (await fetch("http://localhost:8080/rule")).json()
    const apiData = (await apiCall)

    console.log(apiData)
    ruleParagraph.innerHTML = apiData.text
}

function editSelectedText() {
    const selectedText = window.getSelection()

    let range = selectedText.getRangeAt(0)
    let selectionContents = range.extractContents()

    let span = document.createElement("span")
    span.classList.add("fw-bold")
    console.log(selectionContents)
    span.appendChild(selectionContents)
    range.insertNode(span)
}

document.addEventListener("DOMContentLoaded", () => {
    getRules()
})