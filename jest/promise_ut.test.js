
import {asyFun} from "./promise_ut"

test('asyFun ok1 ', () => {
    return asyFun(3).then(data => {
        console.log(data)
        expect(data).toBe(3)
    })
});

test('asyFun ng1 ', () => {
    return asyFun(15).then(data => {
        console.log(data)
        expect(data).toBe(15)
    }).catch(e => {
        expect(e).toBe('error')
    }) 
});

test('asyFun ok2', async () => {
    let data = await asyFun(5)
    console.log(data)
    expect(data).toBe(5)
});

test('asyFun ng2', async () => {
    try {
        let data = await asyFun(20)
        console.log(data)   
    } catch (e) {
        expect(e).toBe('error')
    }
});

test('asyFun ng3', () => {
    expect(asyFun(20)).rejects.toBe('error')
});