import {fetchAll} from "./service_fetch";

describe("mock fetch api test", () => {

    test('mock 200OK deep', async () => {
        console.log("===fetchAll======")

        global.fetch = jest.fn().mockImplementationOnce(() => {
            return new Promise((resolve, reject) => {
                resolve({
                    ok: true,
                    status:200,
                    json: () => {
                        return [{
                            id:1,
                            name: "name"
                        }]
                    }
                })
            })
        });

        let response = await fetchAll()
        let json = await response.json()
        console.log(json);
        expect(json.length).toBe(1);
    });

    test('mock 400NG deep ng', async () => {
        console.log("===fetchAll NG======")

        global.fetch = jest.fn().mockImplementationOnce(() => {
            return new Promise((resolve, reject) => {
                reject({
                    ok: false,
                    status:400,
                    json: () => {
                        return [{
                            id:1,
                            name: "name"
                        }]
                    }
                })
            })
        })

        fetchAll().then(res => {
            console.log("===then 1===");
            console.log(res);
        }).then(res => {
            console.log("===then 2===");
            console.log(res);
        }).catch(err => {
            console.log("===err 1===");
            console.log(err);
        })

    });

});
