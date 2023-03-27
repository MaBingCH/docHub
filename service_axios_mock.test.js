import axios from "axios";

import {getUser} from "./service_axios";

jest.mock("axios");

describe("mock axios api without backend", () => {

    test('getUser ng', async () => {
        // mock response
        axios.get.mockImplementation(() => Promise.resolve({ 
                status: 200,
                data: [{
                    id:1,
                    name:'123',
                    age:13
                }]
            })
        );

        console.log("===getUser======")
        let res = await getUser(1)
        console.log(res.data);
        console.log(res.status);
        expect(res.data.length).toBe(1);
    });

    test('getUser', async () => {
        // mock response
        axios.get.mockImplementation(() => Promise.reject("error user"));

        console.log("===getUser======")
        try {
            await getUser(1)    
        } catch (error) {
            expect(error).toBe("error user");    
        }
    });

});