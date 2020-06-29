import React, {Component} from 'react';
import {Button, Col, Form, Input, Row} from "antd";

class AdminForm extends Component {
  render() {
    const {getFieldDecorator} = this.props.form;
    const {k, diaries} = this.props;
    return (
        <Form onSubmit={(e) => this.props.handleSubmit(e, this.props.form)}>
          <Form.Item style={{display: 'none'}}>
            {getFieldDecorator(`diaryUid`, {
              initialValue: diaries[k] !== undefined ? diaries[k].diaryUid : ''
            })(
                <Input style={{display: 'none'}}/>
            )}
          </Form.Item>
          <Form.Item key={k} style={{display: 'none'}}>
            {getFieldDecorator(`diaryDate`, {
              initialValue: diaries[k] !== undefined ? diaries[k].diaryDate : ''
            })(
                <Input style={{display: 'none'}}/>
            )}
          </Form.Item>
          <Form.Item required={false}>
            {getFieldDecorator(`question`, {
              initialValue: diaries[k] !== undefined ? diaries[k].question : ''
            })(<Input.TextArea rows={2}/>)}
          </Form.Item>
          <Row>
            <Col span={18}>
              {(diaries[k].answer !== null && diaries[k].answer !== '') ?
                  <div>
                    <span style={{fontWeight: 600, color: "red"}}>Odpowiedź: </span>{diaries[k].answer}
                  </div>
                  : ''}
            </Col>
            <Col span={6}>
              <Form.Item style={{marginBottom: 10}}>
                <Button type="primary" htmlType="submit" icon="save" style={{width: '100%'}}>
                  Zapisz
                </Button>
              </Form.Item>
            </Col>

          </Row>


        </Form>
    );
  }
}

export const WrappedAdminForm = Form.create({name: 'admin_form'})(AdminForm);
