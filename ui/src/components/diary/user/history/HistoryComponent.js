import React, {Component} from 'react';
import HistoryList from "./HistoryList";
import {Skeleton} from "antd";

class HistoryComponent extends Component {

  state = {
    diaries: {},
    loading: true,
  };

  componentDidMount() {
    this.props.loadDiary(null).then(() =>
        this.setState({
          loading: false,
        })
    );
  }

  render() {

    const {diaries, initialKeys, location, countEntries} = this.props;

    return (
        <div>
          <Skeleton loading={this.state.loading}>
            <HistoryList
                location={location}
                initialKeys={initialKeys}
                diaries={diaries}
                countEntries={countEntries}

                handleSubmit={this.props.handleSubmit}
                deleteEntry={this.props.deleteEntry}
            />
          </Skeleton>
        </div>
    );
  }
}

export default HistoryComponent;
