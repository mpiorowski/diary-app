import React, {Component} from 'react';
import {
  serviceDiaryAdd,
  serviceDiaryDelete,
  serviceDiaryEdit,
  serviceDiaryFindByDate
} from "../../../services/diaries/DiariesService";
import moment from "moment";
import TodayComponent from "./today/TodayComponent";
import {Route, Switch} from "react-router-dom";
import {openNotification} from "../../../common/notifications/DiaryNotifications";
import HistoryComponent from "./history/HistoryComponent";

const now = moment().format('YYYY-MM-DD');
const startDate = '2019-09-24';

class DiaryUserComponent extends Component {

  state = {
    diaries: {},
    deletedUid: [],
    initialKeys: new Map(),
    countEntries: new Map(),
  };

  componentDidMount() {
    // TODO - api operations
    // serviceOperationsFindAll().then(operations => {
    //   let operationsArray = [];
    //   operations.forEach(operation => operationsArray.push(operation.operationName));
    //   this.setState({
    //     operations: operationsArray,
    //     loading: false,
    //   })
    // })
  }

  loadDiary = (date) => {
    return new Promise((resolve, reject) => {
      serviceDiaryFindByDate(date).then(diaries => {

        let id = 0;
        let diariesArray = {};
        let initialKeys = new Map();
        let diaryDate = '';
        let countEntries = new Map();

        if (date === null) {
          date = moment(now).format('YYYY-MM-DD');
          while (!moment(date).isSame(moment(startDate).subtract(1, 'd'))) {
            date = moment(date).format('YYYY-MM-DD');
            diariesArray = {
              ...diariesArray,
              [date]: {},
            };
            date = moment(date).subtract(1, 'd');
          }
        } else {
          diariesArray = {
            ...diariesArray,
            [date]: {},
          };
        }

        diaries.forEach(diary => {

          diaryDate = moment(diary.diaryDate).format('YYYY-MM-DD');

          diariesArray = {
            ...diariesArray,
            [diaryDate]: {
              ...diariesArray[diaryDate],
              [id]: {
                diaryUid: diary.uid,
                diaryType: diary.diaryType,
                diaryDescription: diary.diaryDescription,
                diaryLike: diary.diaryLike,
                diaryAmount: diary.diaryAmount,
                diaryDate: diary.diaryDate,
                question: diary.question,
                answer: diary.answer,
              }
            }
          };
          initialKeys.set(id, diary.uid);
          id++;
        });

        Object.keys(diariesArray).forEach(dates =>
            countEntries.set(dates, Object.keys(diariesArray[dates]).length)
        );

        this.setState({
          diaries: diariesArray,
          initialKeys: initialKeys,
          countEntries: countEntries,
        });
        resolve(diaries);
      }).catch(error => {
        console.log(error);
        reject();
      });
    });
  };

  handleSubmit = (e, form, mapKeys, k) => {
    e.preventDefault();
    form.validateFields((err, diary) => {
      console.log('request diary', diary);
      if (!err) {
        diary.diaryType = diary.diaryType + ' - ' + diary.diarySType;
        if (mapKeys.has(k)) {
          serviceDiaryEdit(mapKeys.get(k), diary).then(() => {
            openNotification('success');
          }).catch(error => {
            console.log('edit error: ', error);
            openNotification('error');
          });
        } else {
          serviceDiaryAdd(diary).then(response => {
            this.setState({
              mapKeys: mapKeys.set(k, response.uid)
            });
            openNotification('success');
          }).catch(error => {
            console.log('add error: ', error);
            openNotification('error');
          })
        }
        return true;
      }
    });
  };

  deleteEntry = (deletedUid) => {
    return new Promise((resolve, reject) => {
      serviceDiaryDelete(deletedUid).then(() => {
        openNotification('delete');
        resolve();
      }).catch(error => {
        console.log('delete error: ', error);
        reject();
      });
    })
  };

  render() {

    const {match} = this.props;
    return (
        <Switch>
          <Route exact path={`${match.path}/today`} key={1}
                 render={(props) =>
                     <TodayComponent {...props}
                                     currentUser={this.props.currentUser}

                                     handleSubmit={this.handleSubmit}
                                     deleteEntry={this.deleteEntry}
                                     loadDiary={this.loadDiary}

                                     now={now}
                                     diaries={this.state.diaries}
                                     initialKeys={this.state.initialKeys}
                     />
                 }
          />
          <Route exact path={`${match.path}/history`} key={2}
                 render={(props) =>
                     <HistoryComponent {...props}
                                       currentUser={this.props.currentUser}
                                       handleSubmit={this.handleSubmit}
                                       deleteEntry={this.deleteEntry}
                                       loadDiary={this.loadDiary}

                                       diaries={this.state.diaries}
                                       initialKeys={this.state.initialKeys}
                                       countEntries={this.state.countEntries}
                                       startDate={startDate}
                     />
                 }
          />
        </Switch>
    );
  }

}

export default DiaryUserComponent;
