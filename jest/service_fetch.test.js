import {fetchAll, fetchById, fetchData} from "./service_fetch";

describe("fetch api with backend", () => {

    test('fetchAll', async () => {
        console.log("===fetchAll======")
        let response = await fetchAll()
        let json = await response.json()
        console.log(json);
        expect(json.length).toBe(1);
    });

    test('fetch by id', () => {
        console.log("===fetch by id======")
        return fetchById(1).then(res => {
            if (res.ok) {
                return res.json()
            }
        }).then(res => {
            console.log(res)
            expect(res.length).toBe(1);
        })
    });

    test('fetchData all users', () => {
        console.log("===fetcha ll Data======")
        return fetchData().then(data => {
            console.log(data)
        })
    });

});
