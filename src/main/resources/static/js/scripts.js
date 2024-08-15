// scripts.js

let existingTasks = [];

document.addEventListener("DOMContentLoaded", function() {
    fetchTasks();
});

function fetchTasks() {
    fetch('/getAllTask')
        .then(response => response.json())
        .then(tasks => {
            existingTasks = tasks;
            tasks.forEach(task => {
                if (task.status) {
                    addCompletedTask(task.id, task.description);
                } else {
                    addTask(task.id, task.description, task.status);
                }
            });
        })
        .catch(error => console.error('Error fetching tasks:', error));
}

function addTask(id = null, description = "", status = false) {
    const taskRow = createTaskRow(id, description, status);
    document.getElementById("task-list").appendChild(taskRow);
}

function addCompletedTask(id, description) {
    const taskRow = createTaskRow(id, description, true, true);
    document.getElementById("completed-tasks").appendChild(taskRow);
}

function createTaskRow(id, description, status, isCompleted = false) {
    const taskRow = document.createElement("div");
    taskRow.className = "task-row";
    taskRow.dataset.id = id;

    if (!isCompleted) {
        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.name = "completed";
        checkbox.checked = status;
        checkbox.onchange = function() {
            updateTaskStatus(id, checkbox.checked, taskRow);
        };
        taskRow.appendChild(checkbox);
    }

    const label = document.createElement("input");
    label.type = "text";
    label.name = "name";
    label.placeholder = isCompleted ? "Completed task" : "Enter task name";
    label.value = description;
    label.disabled = isCompleted;
    taskRow.appendChild(label);

    const deleteBtn = document.createElement("span");
    deleteBtn.className = "delete-btn";
    deleteBtn.textContent = "×";
    deleteBtn.onclick = function() {
        deleteTask(id, taskRow, isCompleted);
    };
    taskRow.appendChild(deleteBtn);

    return taskRow;
}

function updateTaskStatus(id, status, taskRow) {
    if (id !== null) {
        fetch(`/editTask/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ status: status })
        })
        .then(response => {
            if (response.ok) {
                if (status) {
                    addCompletedTask(id, taskRow.querySelector('input[type="text"]').value);
                    taskRow.remove();
                }
            } else {
                console.error('Error updating task status:', response.statusText);
            }
        })
        .catch(error => console.error('Error:', error));
    }
}

function deleteTask(id, taskRow, isCompleted = false) {
    if (id !== null) {
        fetch(`/deleteTask/${id}`, { method: 'DELETE' })
            .then(response => {
                if (response.ok) {
                    taskRow.remove();
                } else {
                    console.error('Error deleting task:', response.statusText);
                }
            })
            .catch(error => console.error('Error:', error));
    } else {
        taskRow.remove();
    }
}

function submitForm() {
    const tasks = Array.from(document.querySelectorAll("#task-list .task-row")).map(row => ({
        id: row.dataset.id || null,
        description: row.querySelector('input[type="text"]').value,
        status: row.querySelector('input[type="checkbox"]').checked
    }));

    const newTasks = tasks.filter(task => !existingTasks.some(existingTask =>
        existingTask.description === task.description && existingTask.status === task.status
    ));

    fetch('/addTask', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(newTasks)
    })
    .then(response => {
        if (response.ok) {
            window.location.href = "/";
        } else {
            console.error('Error:', response.statusText);
        }
    })
    .catch(error => console.error('Error:', error));
}
