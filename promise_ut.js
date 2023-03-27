export const asyFun = (num) => {
    return new Promise((resolve,reject) => {
        setTimeout(()=> {
            if (num < 10) {
                resolve(num)
            } else {
                reject('error')
            }
        })
    })
}