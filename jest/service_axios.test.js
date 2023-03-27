import {getUser, getUsers, fetchUser} from "./service_axios";

describe("axios api with backend", () => {

    test('getUser ok', async () => {
        console.log("===getUser======")
        let res = await getUser(1)
        console.log(res.data);
        console.log(res.status);
        expect(res.data.length).toBe(1);
    });
    
    test('getUser empty', async () => {
        console.log("===getUser======")
        let res = await getUser(10)
        console.log(res.data)
        expect(res.data.length).toBe(0);
    });
    
    test('getUsers',async () => {
        console.log("===getUsers======")
        let res = await getUsers()
        console.log(res.data)
        expect(res.data.length).toBe(1);
    });
    
    test('fetchUser',(done) => {
        console.log('=========================')
        fetchUser(cbParam => {
            console.log(cbParam)
            expect(cbParam.length).toBe(1);
            done() 
        })
    });

});

