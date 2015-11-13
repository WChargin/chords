import React, {Component, PropTypes} from 'react';
import {Alert, Col, Container, Input, Row} from 'react-bootstrap';

import search from './ChordSearch';

class UserInput extends Component {
    render() {
        return <Row>
            <Col xs={6}>
                <Input
                    type="text"
                    value={this.props.value1}
                    onChange={() => this.props.onValue1Change(
                            this._getValueFor("input1"))}
                    ref="input1"
                />
            </Col>
            <Col xs={6}>
                <Input
                    type="text"
                    value={this.props.value2}
                    onChange={() => this.props.onValue2Change(
                            this._getValueFor("input2"))}
                    ref="input2"
                />
            </Col>
        </Row>;
    }

    _getValueFor(ref) {
        return this.refs[ref].getValue();
    }
}
UserInput.propTypes = {
    value1: PropTypes.string.isRequired,
    value2: PropTypes.string.isRequired,
    onValue1Change: PropTypes.func.isRequired,
    onValue2Change: PropTypes.func.isRequired,
}

// Stolen from:
// https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/parseInt#A_stricter_parse_function
function strictParse(value) {
    if (/^(\-|\+)?([0-9]+|Infinity)$/.test(value)) {
        return Number(value);
    } else {
        return NaN;
    }
}

class Error extends Component {
    render() {
        return <Alert bsStyle="danger">
            <strong>Whoops!</strong> {this.props.children}
        </Alert>;
    }
}

class Result extends Component {
    render() {
        const n1 = strictParse(this.props.value1);
        if (isNaN(n1)) {
            return <Error>The first input must be a number.</Error>;
        }

        const n2 = strictParse(this.props.value2);
        if (isNaN(n2)) {
            return <Error>The second input must be a number.</Error>;
        }

        const result = search(n1, n2);
        if (result.error) {
            return <Error>{result.error}</Error>;
        }

        if (result.position !== null) {
            const [p1, p2] = result.position;
            return <Alert bsStyle="info">
                Your pair is at position ({p1}, {p2})
                on tree T({result.tree}).
            </Alert>;
        } else {
            return <Alert bsStyle="info">
                Your pair is the root of tree T({result.tree}).
            </Alert>;
        }
    }
}
Result.propTypes = {
    value1: PropTypes.string.isRequired,
    value2: PropTypes.string.isRequired,
}

export default class App extends Component {
    constructor() {
        super();
        this.state = {
            value1: "1",
            value2: "1",
        };
    }

    render() {
        return <div className="container">
            <h2>Input values</h2>
            <UserInput
                value1={this.state.value1}
                value2={this.state.value2}
                onValue1Change={(value1) => { this.setState({ value1 }); }}
                onValue2Change={(value2) => { this.setState({ value2 }); }}
            />
            <h2>Result</h2>
            <Result
                value1={this.state.value1}
                value2={this.state.value2}
            />
        </div>;
    }
}
