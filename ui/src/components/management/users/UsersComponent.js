import React, {Component} from 'react';
import {Button} from "antd";
import "./UsersComponent.less";
import UsersTable from "./UsersTable";
import {serviceAddUser, serviceDeleteUser, serviceGetUsers, serviceUpdateUser} from "../../../services/users/UsersServices";
import {serviceGetRoles} from "../../../services/RolesService";
import {WrappedUsersModal} from "./UsersModals";

let tableData = [];

class UsersComponent extends Component {

  state = {
    updateModalVisible: false,
    updatedRecord: {},
    tableData: [],
    tableLoading: true,
    usersRoles: [],
  };

  componentWillMount() {
    tableData = [];
    serviceGetUsers().then(response => {
      response.forEach(user => this.addUserToTable(user));
      this.setState({
        tableData: tableData,
        tableLoading: false,
      });
    }).catch(error => {
      console.log("serviceGetUsers", error);
    });
    serviceGetRoles().then(response => {
      this.setState({
        usersRoles: response,
      });
    }).catch(error => {
      console.log("serviceGetRoles", error);
    });
    console.log(tableData);
  }

  addUserToTable = (user) => {
    tableData.push({
      key: user.userId === null ? null : user.userId,
      userName: user.userName,
      userEmail: user.userEmail,
      userRole: user.userRole,
    });
  };

  handleAddUser = () => {
    const form = this.modalRef.props.form;
    form.validateFields((error, values) => {
      if (error) {
        console.log(error);
        return;
      }
      serviceAddUser(values).then(response => {
        values["key"] = response;
        const newData = [...this.state.tableData];
        newData.push(values);
        this.setState({
          tableData: newData,
          updateModalVisible: false,
        });

      }).catch(error => {
        console.log("serviceUpdateUser", error);
      });
    })
  };

  handleUpdateUser = (key) => {
    const form = this.modalRef.props.form;
    form.validateFields((error, values) => {
      if (error) {
        console.log(error);
        return;
      }
      serviceUpdateUser(key, values).then(response => {

        const newData = [...this.state.tableData];
        const index = newData.findIndex(item => key === item.key);
        const item = newData[index];
        newData.splice(index, 1, {
          ...item,
          ...values,
        });
        this.setState({
          tableData: newData,
          updateModalVisible: false,
        });

      }).catch(error => {
        console.log("serviceUpdateUser", error);
      });
    })
  };

  handleDeleteUser = (key) => {
    serviceDeleteUser(key).then(response => {

      const newData = [...this.state.tableData];
      this.setState({
        tableData: newData.filter(item => item.key !== key),
        updateModalVisible: false,
      });

    }).catch(error => {
      console.log("serviceUpdateUser", error);
    });
  };

  handleModalVisible = (flag, record) => {
    this.setState({
      updateModalVisible: !!flag,
      updatedRecord: record || {},
    });
  };

  saveModalRef = (modalRef) => {
    this.modalRef = modalRef;
  };

  render() {

    return (
        <div>
          <Button
              type="dashed"
              style={{width: '100%', marginBottom: 8}}
              icon="plus"
              onClick={() => this.handleModalVisible(true)}
              className={"add-button"}
              ref={component => {
                /* eslint-disable */
                // this.addBtn = findDOMNode(component);
                /* eslint-enable */
              }}
          >
            Add User
          </Button>
          <div>
            <UsersTable
                tableData={this.state.tableData}
                tableLoading={this.state.tableLoading}
                usersRoles={this.state.usersRoles}
                handleModalVisible={this.handleModalVisible}
                handleDeleteUser={this.handleDeleteUser}
            />
            <WrappedUsersModal
                visible={this.state.updateModalVisible}
                updatedRecord={this.state.updatedRecord}
                usersRoles={this.state.usersRoles}
                wrappedComponentRef={this.saveModalRef}
                handleModalVisible={this.handleModalVisible}
                handleUpdateUser={this.handleUpdateUser}
                handleAddUser={this.handleAddUser}
            />
          </div>
        </div>
    );
  }
}

export default UsersComponent;
