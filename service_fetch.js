export const fetchAll = () => {
    return fetch('http://127.0.0.1:3000/users',{method:'GET'});
}

export const fetchById = (id) => {
    return fetch('http://127.0.0.1:3000/users?id='+id,{method:'GET'});
}

export function fetchData() {
    return new Promise((resolve,reject) => {
        fetchAll().then(response => {
            console.log(response)
            if (response.ok) {
                return response.json()
            }
            reject("error.");
        }).then(response => {
            console.log('Success:', response)
            resolve(response)
        });
    })
}
