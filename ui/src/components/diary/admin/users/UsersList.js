import React, {Component} from 'react';
import {Collapse, Divider} from "antd";
import moment from "moment";
import {WrappedAdminForm} from "../form/AdminForm";

const {Panel} = Collapse;

class UsersList extends Component {

  state = {
    anchorUserUid: '',
    anchorDate: '',
    anchorEntryUid: ''
  };

  componentWillMount() {
    const {location} = this.props;
    const res = location.hash.split("#");
    this.setState({
      anchorUserUid: res[1],
      anchorDate: res[2],
      anchorEntryUid: res[3]
    });

    this.setState({
      userKey: res[1]
    });
    this.setState({
      dateKey: res[2]
    });

  }

  componentDidMount() {
    this.checkAnchor(800).then(() => {
      const anchorElement = document.getElementById(this.state.anchorEntryUid);
      console.log('anchorElement', anchorElement);
      console.log('anchorEntryUid', this.state.anchorEntryUid);
      if (anchorElement != null) {
        anchorElement.scrollIntoView({behavior: "smooth"});
      }
    })
  }

  checkAnchor = (milliseconds) => {
    return new Promise(resolve => setTimeout(resolve, milliseconds))
  };

  userCallback = (key) => {
    this.setState({
      userKey: key
    })
  };
  dateCallback = (key) => {
    this.setState({
      dateKey: key
    })
  };

  render() {

    console.log(this.state.anchorDate);

    const {diaries, usersName, userRole} = this.props;
    let dates;

    const usersUid = Object.keys(diaries);

    const userPanels = usersUid.map(userUid => {
      let countByUser = 0;
      dates = Object.keys(diaries[userUid]);
      const datePanels = dates.map(date => {
        let countByDate = 0;
        const dateDiaries = diaries[userUid][date];
        const keys = Object.keys(dateDiaries);

        const userDiaries = keys.map(k => {
          countByUser++;
          countByDate++;
          let clientSection = () => {
            if (userRole === 'ROLE_CLIENT') {
              if (dateDiaries[k].answer !== '' && dateDiaries[k].answer !== null) {
                return [
                  <div key={1}><span style={{fontWeight: 600}}>Dodatkowe pytanie: </span>{dateDiaries[k].question}</div>,
                  <div key={2}><span style={{fontWeight: 600}}>Odpowiedź: </span>{dateDiaries[k].answer}</div>,
                  <Divider key={3}/>
                ]
              } else if (dateDiaries[k].question !== '' && dateDiaries[k].question !== null) {
                return [
                  <div key={1}><span style={{fontWeight: 600}}>Dodatkowe pytanie: </span>{dateDiaries[k].question}</div>,
                  <Divider key={2}/>
                ]
              } else {
                return <Divider/>
              }
            }
          };

          return (
              <div key={k}>
                <div id={dateDiaries[k] !== undefined ? dateDiaries[k].diaryUid : ''} style={{}}/>
                <div style={{fontSize: 12}}>
                  Stworzony: {dateDiaries[k].createdAt} |
                  Edytowany: {dateDiaries[k].updatedAt} | {usersName.get(userUid)}
                </div>
                <div><span style={{fontWeight: 600}}>Rodzaj: </span>{dateDiaries[k].diaryType}</div>
                <div><span style={{fontWeight: 600}}>Ilość: </span> {dateDiaries[k].diaryAmount}</div>
                <div><span style={{fontWeight: 600}}>Ocena: </span>{dateDiaries[k].diaryLike} / 5</div>
                <div><span style={{fontWeight: 600}}>Uzasadnienie: </span>{dateDiaries[k].diaryDescription}</div>
                {userRole === 'ROLE_CLIENT' ?
                    clientSection() :
                    <Collapse
                        bordered={false}
                        style={{marginBottom: 30}}
                        defaultActiveKey={
                          (!!dateDiaries[k].question) ? ['1'] : ['0']
                        }
                    >
                      <Panel header="Zadaj dodatkowe pytanie" key="1">
                        <WrappedAdminForm
                            handleSubmit={this.props.handleSubmit}
                            diaries={dateDiaries}
                            k={k}
                            keys={keys}
                        />
                      </Panel>
                    </Collapse>
                }
              </div>
          )
        });

        const dateHeader = (
            <div>
              <div style={{width: '20%', display: 'inline-block'}}>
                {date + " - " + moment(date).format('dddd')}
              </div>
              <div style={{width: '40%', display: 'inline-block'}}>
                liczba wpisów: {countByDate}
              </div>
            </div>
        );

        return (
            <Panel header={dateHeader} key={date} className={'diary-panel'}>
              {userDiaries}
            </Panel>
        );

      });
      const userHeader = (
          <div>
            <div style={{width: '20%', display: 'inline-block'}}>
              {usersName.get(userUid)}
            </div>
            <div style={{width: '40%', display: 'inline-block'}}>
              liczba wpisów: {countByUser}
            </div>
          </div>
      );
      return (
          <Panel header={userHeader} key={userUid} className={'diary-panel'}>
            <Collapse bordered={false} activeKey={this.state.dateKey} onChange={this.dateCallback}>
              {datePanels}
            </Collapse>
          </Panel>
      )

    });

    return (
        <Collapse accordion bordered={true} activeKey={this.state.userKey} onChange={this.userCallback}>
          {userPanels}
        </Collapse>
    );
  }
}

export default UsersList;
