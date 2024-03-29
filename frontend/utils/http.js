import { config } from "../config/config";
import { promisic } from "./util";

// callback
// promise
// async await

class Http {
  static async request({ url, data, method = "GET" }) {
    const res = await promisic(wx.request)({
      url: `${config.apiBaseUrl}${url}`,
      data,
      method,
    });
    return res.data;
  }
}
export { Http };
