//call startTest function for test chosen and call updateCheck to update front with the result
function startTest(testName, actionNumber,) {
    fetch(`/startTest?testName=${encodeURIComponent(testName)}`)
        .then(response => response.json())
        .then(data => updateCheck(data.result, actionNumber, 'only'))
        .catch(error => console.error('Error:', error));
}

//call startTestsFromStart function for test chosen and call updateCheck to update front with the results
function startTestsFromStart(testName, actionNumber,listStates) {
    fetch(`/startTestsFromStart?testName=${encodeURIComponent(testName)}&listStates=${encodeURIComponent(listStates)}`)
        .then(response => response.json())
        .then(data => {
            var statesArray = listStates.split(",").map(function(state) {
                return state.replace(/"/g, '').trim();
            });
            statesArray.forEach((step, index) => {
                if (data[step] !== undefined) {
                    updateCheck(data[step], index + 1, 'from-start');
                }
            });
        })
        .catch(error => console.error('Error:', error));
}

// Update front with the test(s) result(s)
function updateCheck(result, actionNumber, type) {
    const checkElement = document.getElementById(`check-${type}-${actionNumber}`);
    if (result) {
        checkElement.textContent = 'True';
        checkElement.className = 'status-true';
    } else {
        checkElement.textContent = 'False';
        checkElement.className = 'status-false';
    }
}
