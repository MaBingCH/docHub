import axios from "axios";

export const get = (url) => {
    return axios.get(url)
}

export const getUsers = () => {
    return axios.get('http://127.0.0.1:3000/users')
}

export const fetchUser = (fn) => {
    axios.get('http://127.0.0.1:3000/users').then(res => {
        fn(res.data)
    }) 
}

export const getUser = (id) => {
    return axios.get('http://127.0.0.1:3000/users?id='+id);
}


