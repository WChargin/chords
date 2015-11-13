import {describe, it} from 'mocha';
import {expect} from 'chai';

import * as Utils from '../src/Utils';

describe('Utils', () => {
    describe('#gcd', () => {
        const test = (a, b, x) => it(`calculates gcd(${a}, ${b}) = ${x}`,
            () => expect(Utils.gcd(a, b)).to.equal(x));
        test(10, 5, 5);
        test(5, 10, 5);
        test(9, 5, 1);
        test(5, 9, 1);
        test(3 * 5 * 7, 5 * 7 * 11, 5 * 7);
    });
});
