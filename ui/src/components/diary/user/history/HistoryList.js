import React, {Component} from 'react';
import {Collapse} from "antd";
import './HistoryList.less';
import moment from "moment";
import DayComponent from "../day/DayComponent";

const {Panel} = Collapse;

class HistoryList extends Component {

  state = {
    activeKey: '',
    anchor: '',
    keys: new Map()
  };

  componentWillMount() {
    const {location} = this.props;
    const res = location.hash.split("#");
    this.setState({
      activeKey: res[1],
      countEntries: this.props.countEntries
    });
  }

  callback = (key) => {
    this.setState({
      activeKey: key
    })
  };

  count = (date, sign) => {
    const current = this.state.countEntries.get(date);
    this.setState({
      countEntries: this.state.countEntries.set(date, current + (1 * sign))
    })
  };


  render() {

    const {diaries, initialKeys, location} = this.props;
    const dates = Object.keys(diaries);

    const isMobile = window.innerWidth <= 576;

    let countEntries = this.state.countEntries;
    let keys;

    const panels = dates.map(date => {

      keys = Object.keys(diaries[date]).map(Number);
      const header = (
          !isMobile ?
              <div>
                <div style={{width: '20%', display: 'inline-block'}}>
                  {date} - {moment(date).format('dddd')}
                </div>
                <div style={{width: '40%', display: 'inline-block'}}>
                  liczba wpisów: {countEntries.get(date)}
                </div>
              </div>
              :
              <div>
                <div style={{width: '50%', display: 'inline-block'}}>
                  {date}
                </div>
                <div style={{width: '50%', display: 'inline-block'}}>
                  liczba wpisów: {countEntries.get(date)}
                </div>
              </div>
      );

      return (
          <Panel header={header} key={date}
                 className={'diary-panel'}>
            <DayComponent
                keys={keys}
                initialKeys={initialKeys}
                diaries={diaries[date]}
                date={date}
                location={location}

                count={this.count}
                handleSubmit={this.props.handleSubmit}
                deleteEntry={this.props.deleteEntry}
            />
          </Panel>
      )
    });

    return (
        <div>
          <Collapse onChange={this.callback} accordion activeKey={this.state.activeKey}>
            {panels}
          </Collapse>
        </div>
    );
  }
}

export default HistoryList;
