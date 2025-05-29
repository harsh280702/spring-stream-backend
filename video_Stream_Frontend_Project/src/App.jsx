import { useState } from 'react'

import './App.css'
import VideoUpload from './Component/VideoUpload'

function App() {
  const [count, setCount] = useState(0)
  const [video, setVideo] = useState("1");

  return (
    <>
      <div  className='flex justify-center py-8'>
      <h1 className='text-4xl font-extrabold  max-w-1/4 text-gray-600 dark:text-gray-100'  > WellCome to Video Streamming AppLication </h1>
      </div>
      
   <div className='max-w-full  flex justify-around m-16'>


   <video
    id="my-video"
    class="video-js"
    controls
    preload="auto"
    width="800"
    height="400"
    data-setup="{}"
  >
    <source src={`http://localhost:8090/api/v1/stream/${video}`} type="video/mp4" />
    <source src="MY_VIDEO.webm" type="video/webm" />

  </video>

 
      <VideoUpload/>
   </div>
    </>
  );
}

export default App
