import {describe, it} from 'mocha';
import {expect} from 'chai';

import search, * as ChordSearchUtils from '../src/ChordSearch';

describe('ChordSearch', () => {
    const test = (a, b, expected) => it(`works for input (${a}, ${b})`,
        () => expect(search(a, b)).to.deep.equal(expected));
    test(10, 22, { tree: 2, position: [ 6, 3 ] });
    test(6, 6, { tree: 6, position: null });
    test(6, 7, { tree: 1, position: [ 6, 2 ] });
    test(0, 0, { tree: 0, position: null });
    test(123, 234, { tree: 3, position: [ 14, 1028 ] });

    const fail = (a, b, message) => it(`works for input (${a}, ${b})`,
        () => {
            const result = search(a, b);
            expect(result).to.be.ok;
            expect(result.error).to.contain(message);
        });
    fail(0, 10, "zero");
    fail(10, 5, "than");
})
