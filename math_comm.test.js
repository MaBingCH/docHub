const sum = require('./math_comm');

describe("math function", () => {
    
    test("sum form common adds two numbers", () => {
        expect(sum(1, 2)).toBe(3);
    });

});
