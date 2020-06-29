import React, {Component} from 'react';
import {Button, Col, Divider, Form, Icon, Input, InputNumber, Popconfirm, Rate, Row, Select} from "antd";
import './DayForm.less';
import {operations} from '../../../../config/SelectConfig';

const {Option} = Select;

class DayForm extends Component {

  static defaultProps = {
    count: () => {
    }
  };

  state = {
    secondType: '',
  };

  componentDidMount() {
    if (this.props.diary !== undefined) {
      const diaryTypeSplit = this.props.diary.diaryType.split(' - ');
      this.secondSelect(diaryTypeSplit[0], false);
    }
  }

  secondSelect = (value, clear) => {
    let o = 0;
    let secondType = '';
    if (operations[value].length > 0) {
      secondType = operations[value].map(sv =>
          <Option value={sv} key={o++}>{sv}</Option>
      );
    } else {
      operations[value].push('Brak');
      secondType = <Option value={'Brak'} key={0}>Brak</Option>;
    }
    if (clear) {
      this.props.form.setFieldsValue({
        diarySType: operations[value][0]
      });
    }
    this.setState({
      secondType: secondType,
    })
  };

  render() {

    const {getFieldDecorator} = this.props.form;
    const {k, diary, diaryDate, initialKeys} = this.props;
    const isMobile = window.innerWidth <= 576;
    const {secondType} = this.state;
    const diaryTypeSplit = diary !== undefined ? diary.diaryType.split(' - ') : '';

    console.log(operations);
    let oo = 0;
    let operationSelect = Object.keys(operations).forEach(operation => {
      return (
          <Option value={operation} key={oo++}>{operation}</Option>
      )
    });

    return (
        <Form onSubmit={(e) => this.props.handleSubmit(e, this.props.form, initialKeys, k)}
              layout={'vertical'}
              key={k}
        >
          <div key={k}>
            <Form.Item key={k} style={{display: 'none'}}>
              {getFieldDecorator('diaryUid', {
                initialValue: diary !== undefined ? diary.diaryUid : ''
              })(
                  <Input style={{display: 'none'}}/>
              )}
            </Form.Item>
            <Form.Item style={{display: 'none'}}>
              {getFieldDecorator('diaryDate', {
                initialValue: diaryDate
              })(
                  <Input style={{display: 'none'}}/>
              )}
            </Form.Item>
            <Row gutter={16}>
              <Col sm={9} xs={24}>
                <Form.Item
                    label={'Wybierz kanał kontaktu'}
                    required={false}
                    key={k}
                >
                  {getFieldDecorator('diaryType', {
                    initialValue: diary !== undefined ? diaryTypeSplit[0] : '',
                    validateTrigger: ['onChange', 'onBlur'],
                    rules: [
                      {
                        required: true,
                        whitespace: true,
                        message: "Proszę uzupełnić",
                      },
                    ],
                  })(
                      <Select key={k} onChange={this.secondSelect}>
                        {operationSelect}
                      </Select>)}
                </Form.Item>
              </Col>
              <Col sm={8} xs={24}>
                <Form.Item
                    label={'Wybierz rodzaj doświadczenia'}
                    required={false}
                    key={k}
                >
                  {getFieldDecorator('diarySType', {
                    initialValue: diary !== undefined ? diaryTypeSplit[1] : '',
                    validateTrigger: ['onChange'],
                    rules: [
                      {
                        required: true,
                        whitespace: true,
                        message: "Proszę uzupełnić",
                      },
                    ],
                  })(
                      <Select key={k}>
                        {secondType}
                      </Select>)}
                </Form.Item>
              </Col>
              <Col sm={3} xs={24}>
                <Form.Item
                    label={'Podaj ile razy'}
                    required={false}
                >
                  {getFieldDecorator('diaryAmount', {
                    initialValue: diary !== undefined ? diary.diaryAmount : 1,
                    validateTrigger: ['onChange', 'onBlur'],
                    rules: [
                      {
                        required: true,
                        message: "Proszę uzupełnić",
                      },
                    ],
                  })(
                      <InputNumber min={0} max={99} style={{width: '100%'}}/>)}
                </Form.Item>
              </Col>
              <Col sm={4} xs={24}>
                <Form.Item label={'Oceń'}>
                  {getFieldDecorator('diaryLike', {
                    initialValue: diary !== undefined ? diary.diaryLike : 0,
                  })(
                      <Rate character={<Icon type="heart" theme="filled" style={{fontSize: 26}}/>} allowHalf/>
                  )}

                </Form.Item>
              </Col>

            </Row>
            <Row gutter={16}>
              <Col sm={24}>
                <div id={diary !== undefined ? diary.diaryUid : ''} style={{position: "absolute", top: "-50px"}}/>
                <Form.Item label={'Opis (rodzaj płatności, uzasadnij swoją odpowiedź)'} required={false}>
                  {getFieldDecorator('diaryDescription', {
                    initialValue: diary !== undefined ? diary.diaryDescription : '',
                    validateTrigger: ['onChange', 'onBlur'],
                    rules: [
                      {
                        required: true,
                        whitespace: true,
                        message: "Proszę uzupełnić",
                      },
                    ],
                  })(<Input.TextArea rows={isMobile ? 4 : 2}/>)}
                </Form.Item>
              </Col>
            </Row>
            {diary !== undefined && diary.question != null && diary.question !== '' ?
                <Row>
                  <div className={'question-div'}>Zadano pytanie do Twojego wpisu: {diary.question}</div>
                  <Form.Item label={'Odpowiedz'} required={false}>
                    {getFieldDecorator('answer', {
                      initialValue: diary !== undefined ? diary.answer : ''
                    })(<Input.TextArea rows={isMobile ? 4 : 2}/>)}
                  </Form.Item>
                </Row>
                : ''
            }
            <Row type="flex" justify="end" gutter={16}>
              <Col sm={2} xs={10}>
                <Form.Item className={'delete-form'}>
                  <Popconfirm
                      className={'header-notification-delete'}
                      placement="bottomRight"
                      title={"Czy na pewno chcesz usunąć wpis?"}
                      onConfirm={() => this.props.removeEntry(k, initialKeys, diaryDate)}
                      okText="Tak"
                      cancelText="Nie">
                    <Button icon="delete" style={{width: '100%'}}>
                      Usuń
                    </Button>
                  </Popconfirm>
                </Form.Item>
              </Col>
              <Col sm={8} xs={14}>
                <Form.Item>
                  <Button type="primary" htmlType="submit" icon="save" style={{width: '100%'}}>
                    Zapisz
                  </Button>
                </Form.Item>
              </Col>
            </Row>

            <Divider className={'diary-divider'}/>
          </div>
        </Form>
    );
  }
}

export const WrappedDiaryForm = Form.create({name: 'dynamic_form_item'})(DayForm);
