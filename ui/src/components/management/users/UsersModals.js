import React, {Component} from 'react';
import {Form, Input, Modal, Select} from "antd";

const FormItem = Form.Item;
const Option = Select.Option;

class UsersModals extends Component {

  createRoleSelect = (usersRoles) => {
    return (
        <Select placeholder="user role">
          {usersRoles.map(role => {
            return (
                <Option key={role["roleId"]} value={role["roleDesc"]}>{role["roleDesc"]}</Option>
            )
          })}
        </Select>
    )
  };

  createModalForm = (isPassword, getFieldDecorator, formItemLayout, usersRoles, updatedRecord) => {
    return (
        <Form layout="horizontal"
              {...formItemLayout}
        >
          <FormItem style={{display: "none"}}>
            {getFieldDecorator('userId', {
              initialValue: updatedRecord['key'],
            })(<Input style={{display: "none"}}/>)}
          </FormItem>
          <FormItem label="Username" required={false}>
            {getFieldDecorator('userName', {
              validateTrigger: ['onChange', 'onBlur'],
              rules: [{
                required: true,
                whitespace: true,
                max: 60,
                pattern: /^\S+$/,
                message: "Please input user's name. Max 60 characters. No spaces.",
              }],
              initialValue: updatedRecord['userName'],
            })(
                <Input placeholder="user name (max: 60)"/>
            )}
          </FormItem>
          <FormItem label="Email" required={false}>
            {getFieldDecorator('userEmail', {
              validateTrigger: ['onBlur'],
              rules: [{
                required: true,
                whitespace: true,
                max: 60,
                pattern: /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/,
                message: "Please input user's email. Max 60 characters. Email format.",
              }],
              initialValue: updatedRecord['userEmail']
            })(
                <Input placeholder="user email (max: 60)"/>
            )}
          </FormItem>
          {isPassword ?
              <FormItem label="Password" required={false}>
                {getFieldDecorator('userPassword', {
                  validateTrigger: ['onChange', 'onBlur'],
                  rules: [{
                    required: true,
                    whitespace: true,
                    max: 60,
                    pattern: /^\S+$/,
                    message: "Please input user's password. Max 60 characters. No spaces.",
                  }]
                })(
                    <Input placeholder="user password (max: 60)"/>
                )}
              </FormItem> : ""
          }
          <FormItem label="Role" required={false}>
            {getFieldDecorator('userRole', {
              validateTrigger: ['onChange', 'onBlur'],
              rules: [{
                required: true,
                message: "Please choose user role.",
              }],
              initialValue: updatedRecord['userRole']
            })(
                this.createRoleSelect(usersRoles)
            )}
          </FormItem>
        </Form>
    )
  };

  render() {

    const {form, updatedRecord, usersRoles} = this.props;
    const {getFieldDecorator} = form;
    const formItemLayout = {
      labelCol: {span: 6},
      wrapperCol: {span: 16},
    };

    if (updatedRecord["userName"]) {
      return (
          <Modal
              width={640}
              bodyStyle={{padding: '32px 30px 20px 30px'}}
              destroyOnClose
              title="Edit User"
              visible={this.props.visible}
              onCancel={() => this.props.handleModalVisible(false, {})}
              afterClose={() => this.props.handleModalVisible()}
              okText={"Edit"}
              onOk={() => this.props.handleUpdateUser(updatedRecord.key)}
              // footer={this.renderFooter(currentStep)}
          >
            {this.createModalForm(false, getFieldDecorator, formItemLayout, usersRoles, updatedRecord)}
          </Modal>
      );
    }
    return (
        <Modal
            width={640}
            bodyStyle={{padding: '32px 30px 20px 30px'}}
            destroyOnClose
            title="Add User"
            visible={this.props.visible}
            onCancel={() => this.props.handleModalVisible(false, {})}
            afterClose={() => this.props.handleModalVisible()}
            okText={"Add"}
            onOk={() => this.props.handleAddUser()}
            // footer={this.renderFooter(currentStep)}
        >
          {this.createModalForm(true, getFieldDecorator, formItemLayout, usersRoles, updatedRecord)}
        </Modal>
    )


  }
}

export const WrappedUsersModal = Form.create()(UsersModals);
