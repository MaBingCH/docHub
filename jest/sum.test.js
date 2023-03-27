import sum,{add} from './sum'

describe("sum function", () => {

    test("sum from es adds two numbers", () => {
        expect(sum(1, 2)).toBe(3);
    });
    
    test("add from es adds two numbers", () => {
        expect(add(1, 2)).toBe(3);
    });
});

