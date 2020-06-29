import React, {Component} from 'react';
import {WrappedAdminForm} from "../form/AdminForm";
import {Collapse, Divider} from "antd";
import moment from "moment";

const {Panel} = Collapse;

class NewestList extends Component {
  render() {
    const {diaries, userRole} = this.props;

    const entries = diaries.map((diary, k) => {

      let clientSection = () => {
        if (userRole === 'ROLE_CLIENT') {
          if (diaries[k].answer !== '' && diaries[k].answer !== null) {
            return [
              <div key={1}><span style={{fontWeight: 600}}>Dodatkowe pytanie: </span>{diaries[k].question}</div>,
              <div key={2}><span style={{fontWeight: 600}}>Odpowiedź: </span>{diaries[k].answer}</div>,
              <Divider key={3}/>
            ]
          } else if (diaries[k].question !== '' && diaries[k].question !== null) {
            return [
              <div key={1}><span style={{fontWeight: 600}}>Dodatkowe pytanie: </span>{diaries[k].question}</div>,
              <Divider key={2}/>
            ]
          } else {
            return <Divider/>
          }
        }
      };

      return (
          <div key={k}>
            <div style={{fontSize: 12}}>
              Stworzony: {diaries[k].createdAt} |
              Edytowany: {diaries[k].updatedAt}
            </div>
            <div><span style={{fontWeight: 600}}>Użytkownik: </span>{diaries[k].userName}</div>
            <div><span
                style={{fontWeight: 600}}>Dzień: </span>{moment(diaries[k].diaryDate).format('dddd - YYYY-MM-DD')}</div>
            <div><span style={{fontWeight: 600}}>Rodzaj: </span>{diaries[k].diaryType}</div>
            <div><span style={{fontWeight: 600}}>Ilość: </span> {diaries[k].diaryAmount}</div>
            <div><span style={{fontWeight: 600}}>Ocena: </span>{diaries[k].diaryLike} / 5</div>
            <div><span style={{fontWeight: 600}}>Uzasadnienie: </span>{diaries[k].diaryDescription}</div>
            {userRole === 'ROLE_CLIENT' ?
                clientSection() :
                <Collapse
                    bordered={false}
                    style={{marginBottom: 30}}
                    defaultActiveKey={
                      (!!diaries[k].question) ? ['1'] : ['0']
                    }

                >
                  <Panel header="Zadaj dodatkowe pytanie" key="1">
                    <WrappedAdminForm
                        handleSubmit={this.props.handleSubmit}
                        diaries={diaries}
                        k={k}
                    />
                  </Panel>
                </Collapse>
            }

          </div>
      )
    });

    return (
        <div>
          {entries}
        </div>
    );
  }
}

export default NewestList;
