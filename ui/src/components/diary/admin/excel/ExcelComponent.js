import React, {Component} from 'react';
import {Button, DatePicker, Form, Icon, Spin} from 'antd';
import moment from "moment";
import {serviceExcelDownload} from "../../../../services/excel/ExcelServices";

const {RangePicker} = DatePicker;
const spinIcon = <Icon type="loading" style={{fontSize: 18}} spin/>;

class ExcelComponent extends Component {

  state = {
    downloading: false,
  };

  handleSubmit = e => {
    e.preventDefault();
    this.setState({
      downloading: true
    });
    this.props.form.validateFields((err, dates) => {
      if (!err) {
        const data = [dates['range-picker'][0].format('YYYY-MM-DD'), dates['range-picker'][1].format('YYYY-MM-DD')];
        serviceExcelDownload(data).then(() =>
            this.setState({
              downloading: false
            })
        );
      }
    })
  };

  render() {
    const dateFormat = 'YYYY-MM-DD';
    const {getFieldDecorator} = this.props.form;
    return (
        <div>
          <Form onSubmit={this.handleSubmit}>
            <Form.Item label="Wybierz zakres dat">
              {getFieldDecorator(
                  'range-picker',
                  {
                    size: 'large',
                    initialValue: [moment('2019-09-24', dateFormat), moment('2019-10-24', dateFormat)],
                  }
              )(
                  <RangePicker
                      placeholder={['Początek', 'Koniec']}
                  />
              )}

            </Form.Item>
            <Form.Item>
              <Button type="primary" htmlType="submit" style={{width: 300}}>
                {this.state.downloading ? <Spin indicator={spinIcon} className={"login-spin"}/> : ""}
                Pobierz
              </Button>
            </Form.Item>
          </Form>
        </div>
    );
  }
}

export const WrappedExcelForm = Form.create({name: 'time_related_controls'})(ExcelComponent);
