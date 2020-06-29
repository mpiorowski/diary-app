import React, {Component} from 'react';
import {serviceNotificationFindByType} from "../../services/notifications/NotificationsService";

class TestApi extends Component {

  state = {
    results: ''
  };

  componentWillMount() {
    serviceNotificationFindByType('diary-question').then(results => {
      this.setState({
        results: results
      });
    })
  }


  render() {
    return (
        <div>
          <h1>TEST API</h1>
          <pre style={{fontSize: '10px'}}>{JSON.stringify(this.state.results, null, 2)}</pre>
        </div>
    );
  }
}

export default TestApi;
