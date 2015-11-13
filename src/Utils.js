/*
 * Mathematical utility functions.
 */
export function gcd(a, b) {
    let c;
    while (a !== 0) {
        c = a;
        a = b % a;
        b = c;
    }
    return b;
}
