import * as Utils from './Utils';

export default function search(a, b) {
    if (a === b) {
        return { tree: a, position: null };
    }
    if (a > b) {
        const error = "The second input can't be larger than the first input.";
        return { error };
    }
    if (a === 0) {
        const error =
            "You can't have a zero first input and non-zero second input.";
        return { error };
    }

    const gcd = Utils.gcd(a, b);
    const pair = findPair(a / gcd, b / gcd);
    return {
        tree: gcd,
        position: pair,
    };
}

function findPair(a, b) {
    if (a === 1) {
        return [ b - 1, 1 ];
    }

    const c = b % a;
    const t = (b - c) / a;

    const [ ra, rb ] = findPair(c, a);
    const factor = Math.pow(2, t - 1);
    return [ ra + t, (2 * rb - 1) * factor + 1 ];
}
