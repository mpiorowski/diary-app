import React, {Component} from 'react';
import {Button, Col, Icon, Row} from "antd";
import {WrappedDiaryForm} from "../day/DayForm";

let id = -1;
class DayComponent extends Component {

  state = {
    keys: this.props.keys
  };

  static defaultProps = {
    count: () => {
    }
  };

  componentDidMount() {
    const res = this.props.location.hash.split("#");
    const anchorElement = document.getElementById(res[2]);
    if (anchorElement != null) {
      anchorElement.scrollIntoView({behavior: "smooth"});
    }
  }

  removeEntry = (k, initialKeys, date) => {
    const keys = this.state.keys;
    if (initialKeys.has(k)) {
      this.props.deleteEntry(initialKeys.get(k)).then(() => {
        this.setState({
          keys: keys.filter(key => key !== k)
        });
      });
    } else {
      this.setState({
        keys: keys.filter(key => key !== k)
      });
    }
    this.props.count(date, -1);
  };

  addEntry = (date) => {
    const keys = [id--].concat(this.state.keys);
    this.setState({
      keys: keys
    });
    this.props.count(date, 1);
  };

  render() {
    const {date, diaries, initialKeys} = this.props;
    const keys = this.state.keys;

    const entries = keys.map(k => {
      k = parseInt(k);
      return (
          <WrappedDiaryForm
              key={k}
              k={k}

              diary={diaries[k]}
              diaryDate={date}
              initialKeys={initialKeys}

              removeEntry={this.removeEntry}

              handleSubmit={this.props.handleSubmit}
              deleteEntry={this.props.deleteEntry}
          />
      )
    });

    return (
        <div>
          <Row gutter={16}>
            <Col span={24}>
              <Button type="dashed" onClick={() => this.addEntry(date)} style={{width: '100%', marginBottom: 30}}>
                <Icon type="plus"/> Dodaj wpis
              </Button>
            </Col>
          </Row>
          {entries}
        </div>
    )
  }
}

export default DayComponent;
