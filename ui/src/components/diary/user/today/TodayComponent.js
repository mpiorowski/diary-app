import React, {Component} from 'react';
import moment from "moment";
import {Skeleton} from "antd";
import DayComponent from "../day/DayComponent";

class TodayComponent extends Component {

  state = {
    loading: true
  };

  componentDidMount() {
    this.props.loadDiary(this.props.now).then(() =>
        this.setState({
          loading: false,
        })
    );
  }

  render() {
    const {now, diaries, initialKeys, location} = this.props;
    let keys = Array.from(this.props.initialKeys.keys());

    return (
        <div>
          <div style={{textAlign: 'center', fontSize: '22px'}}>
            {now + " - " + moment(now).format('dddd')}
          </div>
          <Skeleton loading={this.state.loading}>
            <DayComponent
                keys={keys}
                initialKeys={initialKeys}
                diaries={diaries[now]}
                date={now}
                location={location}

                handleSubmit={this.props.handleSubmit}
                deleteEntry={this.props.deleteEntry}
            />
          </Skeleton>
        </div>
    )
  }
}

export default TodayComponent;
