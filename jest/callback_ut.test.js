import { jest } from "@jest/globals"
import {fetchData} from "./callback_ut"

test('callback', (done) => {
    fetchData(param => {
        expect(param).toBe('hello')
        done() 
    })
})

test('callback mock', () => {
    jest.useFakeTimers()
    const cb = jest.fn()
    fetchData(cb)
    expect(cb).not.toHaveBeenCalled();
    jest.runAllTimers()
    expect(cb).toHaveBeenCalled()
    expect(cb).toHaveBeenCalledTimes(1)
})