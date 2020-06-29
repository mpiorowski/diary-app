import React, {Component} from 'react';

class HomeComponent extends Component {

  componentDidMount() {
    // throw new Error("blad!");
  }

  doError = () => {
    // throw new Error("blad!");
  };

  render() {
    return (
        <div>
          {/*<button onClick={this.doError}></button>*/}
          <h3>Witamy na naszej stronie :)</h3>
        </div>
    );
  }
}

export default HomeComponent;
