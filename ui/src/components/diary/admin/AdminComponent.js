import React, {Component} from 'react';
import {Route} from "react-router-dom";
import {serviceOperationsFindAll} from "../../../services/operations/OperationsService";
import moment from "moment";
import UsersComponent from "./users/UsersComponent";
import NewestComponent from "./newest/NewestComponent";
import {WrappedExcelForm} from "./excel/ExcelComponent";

// let requests;
// let checkedRequests = 0;
// let isError = false;
let now = moment().format('YYYY-MM-DD');
let startDate = '2019-09-24';

class AdminComponent extends Component {

  state = {
    diaries: {},
    initialKeys: [],
    operations: [{
      operationName: '',
    }],
    loading: true,
  };

  componentDidMount() {
    serviceOperationsFindAll().then(operations => {
      let operationsArray = [];
      operations.forEach(operation => operationsArray.push(operation.operationName));
      this.setState({
        operations: operationsArray,
        loading: false,
      })
    })
  }

  render() {
    const {match} = this.props;
    const userRole = this.props.currentUser.authorities[0].authority;

    return (
        <div>
          <Route exact path={`${match.path}/users`}
                 render={(props) =>
                     <UsersComponent
                         {...props}
                         userName={this.props.currentUser.userName}
                         userRole={userRole}
                         now={now}
                         startDate={startDate}
                     />
                 }
          />
          <Route exact path={`${match.path}/newest`}
                 render={(props) =>
                     <NewestComponent
                         {...props}
                         userName={this.props.currentUser.userName}
                         userRole={userRole}
                         now={now}
                         startDate={startDate}
                     />
                 }
          />

          {userRole === 'ROLE_ADMIN' ?
              <Route exact path={`${match.path}/excel`}
                     render={(props) =>
                         <WrappedExcelForm
                             {...props}
                             userName={this.props.currentUser.userName}
                             now={now}
                             startDate={startDate}
                         />
                     }
              /> : ''}
        </div>
    );
  }
}

export default AdminComponent;
