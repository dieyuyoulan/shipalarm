import time
import numpy as np
import cv2
from PIL import Image
from core import process, predict

video_fps       = 25.0

def c_main(path, model, ext, video_save_path=""):
    global img_y, image_info
    image_data = process.pre_process(path)
    print(image_data)
    if ext == "jpg" or ext == "jpeg":
        image_info = predict.predict(image_data, model, ext)
    elif ext == "avi" or ext == "mp4":
        capture = cv2.VideoCapture(path)
        if video_save_path!="":
            fourcc  = cv2.VideoWriter_fourcc(*'XVID')
            size    = (int(capture.get(cv2.CAP_PROP_FRAME_WIDTH)), int(capture.get(cv2.CAP_PROP_FRAME_HEIGHT)))
            out     = cv2.VideoWriter(video_save_path, fourcc, video_fps, size)
        while (True):
            t1 = time.time()
            # 读取某一帧
            ref, frame = capture.read()
            # 格式转变，BGRtoRGB
            if frame is not None:

                frame = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
                # 转变成Image
                frame = Image.fromarray(np.uint8(frame))
                frame = cv2.cvtColor(np.asarray(frame), cv2.COLOR_RGB2BGR)
                img_y, image_info = model.detect(frame)
                cv2.imshow("video", img_y)
                c = cv2.waitKey(1) & 0xff
                if video_save_path != "":
                    out.write(frame)
            else:
                break

            # if c == 27:
            #     capture.release()
            #     break
        capture.release()
        out.release()
        cv2.destroyAllWindows()


    return image_data[1] + '.' + ext\
        , image_info


if __name__ == '__main__':
    pass
